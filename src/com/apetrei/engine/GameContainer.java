package com.apetrei.engine;

import com.apetrei.engine.input.Input;
import com.apetrei.engine.input.InputType;
import com.apetrei.engine.physics.PhysicsSystem2D;
import com.apetrei.engine.renderer.ImageLoader;
import com.apetrei.engine.renderer.Renderer;
import com.apetrei.engine.renderer.Window;

import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

public class GameContainer implements Runnable {

    static private GameContainer gameContainer;

    //Thread pe care va rula enginul
    private final Thread thread;
    private final Window window;
    private final Renderer renderer;
    private final Input input;
    private final ObjectManager objectManager;
    private final PhysicsSystem2D physicsSystem;

    private  boolean running = false;

    private GameContainer(){
        //Initializari importante
        thread = new Thread(this);
        window = new Window();
        renderer = new Renderer(this);
        input = new Input(this);
        objectManager = new ObjectManager(this);
        physicsSystem = new PhysicsSystem2D();

    }

    //Nu consider ca un singleton e ideal aici, dar e o scurtatura usoara pentru a permite serializarea catorva obiecte.
    static public  GameContainer getInstance(){
        if (gameContainer == null) {
            gameContainer = new GameContainer();
            return  gameContainer;
        }
        else {
            return  gameContainer;
        }
    }

    public void start(){
        ImageLoader.getInstance();      //Pre initializare
        thread.run();
    }

    public void stop(){

    }

    //The Runnable interface should be implemented by any class whose instances are intended to be executed by a thread.
    // The class must define a method of no arguments called run.
    public void run(){

        //Atat timp cat jocul ruleaza
        running = true;
        //Daca e necesara redesenarea jocului
        boolean render = true;

        //Monitorizare performanta
        float firstTime = 0;
        float lastTime = (float) ( System.nanoTime() / 10.0e8) ;
        float frameTime = 0;
        float unprocessedTime = 0;

        while(running) {

            //Presupunem ca nu trebuie sa redesenam jocul
            render = false;

            firstTime = (float) (System.nanoTime() / 10.0e8);
            frameTime = firstTime - lastTime;
            lastTime = firstTime;

            unprocessedTime += frameTime;

            //PHYSICS UPDATE
            physicsSystem.updatePhysics(frameTime);
            //UPDATE
            objectManager.updateObjects(frameTime);

            //TESTING ZONE
            if(this.getInput().isKey( KeyEvent.VK_F1 , InputType.DOWN)) {
                objectManager.saveGame();
            }
            if(this.getInput().isKey( KeyEvent.VK_F2 , InputType.DOWN)) {
                objectManager.restoreGame();
            }

            //Imita performanta proasta pentru teste
            try {
                //   TimeUnit.MICROSECONDS.sleep( 8666);
            }catch (Exception e){
            }

            //IMPUT UPDATE
            input.nextEvent();


            if(ConfigHandler.isDebugMode()    ) {
             //   System.out.println("Current FPS:" + 1 / frameTime + "\r");
            }

            //RENDERING
            while (unprocessedTime >= ConfigHandler.getUpdateCap()) {
                unprocessedTime -= ConfigHandler.getUpdateCap();
                render = true;
            }

            if (render) {
                renderer.Render();
                renderer.Display();
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        dispose();
    }

    private void dispose(){
        //don't know
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
}
