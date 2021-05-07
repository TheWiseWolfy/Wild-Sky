package com.apetrei.engine;

import com.apetrei.engine.gui.HUDManager;
import com.apetrei.engine.gui.MenuManager;
import com.apetrei.engine.input.Input;
import com.apetrei.engine.objects.ObjectManager;
import com.apetrei.engine.physics.PhysicsSystem2D;
import com.apetrei.engine.renderer.ImageLoader;
import com.apetrei.engine.renderer.Renderer;
import com.apetrei.engine.renderer.Window;
import com.apetrei.engine.scenes.GameplayScene;
import com.apetrei.engine.scenes.MainMenuScene;
import com.apetrei.engine.scenes.Scene;

import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class GameContainer implements Runnable {

    //Thread pe care va rula enginul
    private final Thread thread;
    //private final Thread threadTest;

    private final Window window;
    private final Renderer renderer;
    private final Input input;
    private final ObjectManager objectManager;

    private final HUDManager hudManager;
    private final MenuManager menuManager;
    private final PhysicsSystem2D physicsSystem;

    //Scene stack
    Stack<Scene> sceneStack = new Stack<>();

    Scene sceneToBeUsed;
    private boolean popScene = false;
    private boolean running = false;

    public GameContainer() {
        //Initializari importante
        thread = new Thread(this);

        window = new Window();
        renderer = new Renderer(this);
        hudManager = new HUDManager(this);
        menuManager = new MenuManager(this);
        input = new Input(this);
        physicsSystem = new PhysicsSystem2D();

        objectManager = new ObjectManager(this);
        objectManager.attachObserver(hudManager);
        objectManager.attachObserver( physicsSystem);

        MainMenuScene mainMenuScene = new MainMenuScene(this);
        mainMenuScene.init(this);
        sceneStack.add(mainMenuScene);
    }

    public void start() {
        ImageLoader.getInstance();      //Pre initializare
        thread.run();
    }

    //The Runnable interface should be implemented by any class whose instances are intended to be executed by a thread.
    // The class must define a method of no arguments called run.
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

        while (running) {

            //Presupunem ca nu trebuie sa redesenam jocul
            render = false;
            firstTime = (float) (System.nanoTime() / 10.0e8);
            frameTime = firstTime - lastTime;
            lastTime = firstTime;
            unprocessedTime += frameTime;

            //UPDATE//
            sceneStack.peek().update(this,frameTime);
            input.nextEvent();
            ///////////

            if (ConfigHandler.isDebugMode()) {
                //   System.out.println("Current FPS:" + 1 / frameTime + "\r");
            }

            try {
               //   TimeUnit.MICROSECONDS.sleep( 8666);
            }catch (Exception e){

            }

            //RENDERING
            while (unprocessedTime >= ConfigHandler.getUpdateCap()) {
                unprocessedTime -= ConfigHandler.getUpdateCap();
                render = true;
            }

            if (render) {
                renderer.PrepareRender();
                ////RENDER////
                sceneStack.peek().render(this);
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
                sceneToBeUsed.init(this);
                sceneStack.add( sceneToBeUsed);
                sceneToBeUsed = null;
            } else if( popScene){
                sceneStack.pop();
                sceneStack.peek().init(this);
                popScene =false;
            }
        }//END WHILE
        window.close();
    }

    //_____________________SCENE_STACK_____________________

    public void close(){
        running = false;

    }

    public void goBack(){
        if(sceneStack.size() > 1) {
            popScene = true;
        }
    }

    public void goTo(GameplayScene newScene){
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
}
