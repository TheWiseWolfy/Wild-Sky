package com.apetrei.engine;

import com.apetrei.engine.event.GlobalEventQueue;
import com.apetrei.engine.gui.HUDManager;
import com.apetrei.engine.gui.MenuManager;
import com.apetrei.engine.input.Input;
import com.apetrei.engine.input.InputType;
import com.apetrei.engine.objects.ObjectManager;
import com.apetrei.engine.physics.PhysicsSystem2D;
import com.apetrei.engine.providers.ConfigHandler;
import com.apetrei.engine.renderer.Renderer;
import com.apetrei.engine.renderer.Window;
import com.apetrei.engine.scenes.MainMenuScene;
import com.apetrei.engine.scenes.Scene;
import com.apetrei.engine.providers.DatabaseManager;
import com.apetrei.engine.providers.ResourceLoader;

import java.awt.event.KeyEvent;
import java.util.Stack;

/*!
 * Clasa care centralizeaza toate sistemele jocului si le face vizibile in interior.
 */
public class GameContainer implements Runnable {
    //Thread pe care va rula enginul
    private final Thread thread;

    private final Window window;
    private final Renderer renderer;
    private final Input input;
    private final ObjectManager objectManager;

    private final HUDManager hudManager;
    private final MenuManager menuManager;
    private final PhysicsSystem2D physicsSystem;
    private final GlobalEventQueue globalEventQueue;

    //SCENE SYSTEM
    Stack<Scene> sceneStack = new Stack<>();
    private Scene sceneToBeUsed;
    private boolean popScene = false;
    private boolean running = false;

    public GameContainer() {
        //Engine Initialization
        thread = new Thread(this);
        window = new Window();
        renderer = new Renderer(this);
        hudManager = new HUDManager(this);
        menuManager = new MenuManager(this);
        input = new Input(this);
        physicsSystem = new PhysicsSystem2D(this);
        globalEventQueue = new GlobalEventQueue();

        objectManager = new ObjectManager(this);
        objectManager.attachObserver(hudManager);
        objectManager.attachObserver(physicsSystem);
        objectManager.attachObserver(physicsSystem.getWindEffect() );
        objectManager.attachObserver(renderer.getCamera());

        //Loading stuffs
        DatabaseManager.getInstance().updateConfigClass();
        ResourceLoader.getInstance();

        //Game Initialization
        MainMenuScene mainMenuScene = new MainMenuScene(this);
        mainMenuScene.init();
        sceneStack.add(mainMenuScene);
    }

    public void start() {
        ResourceLoader.getInstance();      //Pre initializare
        thread.run();
    }

    public void run() {

        //Atat timp cat jocul ruleaza
        running = true;
        //Daca e necesara redesenarea jocului
        boolean render = true;

        //Monitorizare performanta
        float firstTime = 0;
        float lastTime = (float) (System.nanoTime() / 10.0e8);
        float frameTime = 0;
        float unprocessedTime = 0;

        //Main game loop
        while (running) {

            //Presupunem ca nu trebuie sa redesenam jocul
            render = false;
            firstTime = (float) (System.nanoTime() / 10.0e8);
            frameTime = firstTime - lastTime;
            lastTime = firstTime;
            unprocessedTime += frameTime;

            //MISC CODE
            if( input.isKey(KeyEvent.VK_F1 , InputType.DOWN ) ){
                ConfigHandler.setDebugMode( !ConfigHandler.isDebugMode() );
                System.out.println(ConfigHandler.isDebugMode());
            }

            //UPDATE//
            sceneStack.peek().update(frameTime);       //Actualizam scena curenta
            input.nextEvent();
            globalEventQueue.nextEvent();
            //////////

            //RENDERING
            while (unprocessedTime >= ConfigHandler.getUpdateCap()) {
                unprocessedTime -= ConfigHandler.getUpdateCap();
                render = true;
            }

            if (render) {
                renderer.PrepareRender();
                ////RENDER////
                sceneStack.peek().render();
                /////////////
                renderer.Render();
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //CHANGE SCENE AT THE END OF FRAME
            if(sceneToBeUsed != null){
                sceneToBeUsed.init();
                sceneStack.add( sceneToBeUsed);
                sceneToBeUsed = null;
            } else if(popScene){
                sceneStack.pop();
                sceneStack.peek().init();
                popScene =false;
            }
        }//END WHILE
        DatabaseManager.getInstance().updateDataBase();     //Salvam baza de date o ultima oara
        DatabaseManager.getInstance().closeConnection();       //Si apoi o inchidem
        window.close();
    }

    //_____________________SCENE_STACK_____________________
    public void close(){
        running = false;
    }

    //Fuctia folosita pentru a cobora in stack
    public void goBack(){
        if(sceneStack.size() > 1) {
            popScene = true;
        }
    }
    //Fuctia folosita cand vrem sa ne ducem in alta scena.
    public void goTo(Scene newScene){
        if( newScene.getClass() != sceneStack.peek().getClass() ) {
            sceneToBeUsed = newScene;
        }
    }

    //______________________GETTERS__________________________

    public ObjectManager getObjectManager() {
        return objectManager;
    }
    public Window getWindow() {
        return window;
    }
    public Input getInput() {
        return input;
    }
    public Renderer getRenderer() {
        return renderer;
    }
    public PhysicsSystem2D getPhysicsSystem() {
        return physicsSystem;
    }
    public HUDManager getHudManager() {
        return hudManager;
    }
    public MenuManager getMenuManager() {
        return menuManager;
    }
    public GlobalEventQueue getGlobalEventQueue() {
        return globalEventQueue;
    }
}
