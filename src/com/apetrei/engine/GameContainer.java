package com.apetrei.engine;

import com.apetrei.engine.physics.PhysicsSystem2D;
import com.apetrei.engine.renderer.Renderer;

public class GameContainer implements Runnable {


    //Thread pe care va rula enginul
    private Thread thread;
    private Window window;
    private Renderer renderer;
    private Input input;
    private ObjectManager objectManager;
    private PhysicsSystem2D physicsSystem;

    private  boolean running = false;


    public GameContainer(){
        //Initializari importante
        thread = new Thread(this);
        window = new Window();
        renderer = new Renderer(this);
        input = new Input(this);
        objectManager = new ObjectManager();
        physicsSystem = new PhysicsSystem2D();
    }

    public void start(){
        //Pornim un thread separat
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
        double firstTime = 0;
        double lastTime = System.nanoTime() / 10.0e8;
        double frameTime = 0;
        double unprocessedTime = 0;

        while(running) {

            //Presupunem ca nu trebuie sa redesenam jocul
            render = false;

            firstTime = System.nanoTime() / 10.0e8;
            frameTime = firstTime - lastTime;
            lastTime = firstTime;

            unprocessedTime += frameTime;

            //PHYSICS UPDAT
            physicsSystem.Update((float)frameTime);
            //UPDATE
            objectManager.updateObjects(frameTime);

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
    }

    //_______________________Fluff________________________________

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
