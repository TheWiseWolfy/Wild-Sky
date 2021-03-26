package com.apetrei.engine;

import com.apetrei.engine.components.Drawing;

public class GameContainer implements Runnable {


    //Thread pe care va rula enginul
    private Thread thread;
    private Window window;
    private Renderer renderer;
    private Input input;

    private ObjectManager objectManager;
    private  boolean running = false;

    //Propietatile jocului
    private  final double UPDATE_CAP= 1.0/60.0;
    private int width = 640,
                height =480;
    private float scale = 2f;
    private String title = "Engine v1.0";

    public GameContainer(){
        //Initializari importante
        thread = new Thread(this);
        window = new Window(this);
        renderer = new Renderer(this);
        input = new Input(this);
        objectManager = new ObjectManager(this);
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

            //UPDATE
          //  game.update(this, frameTime);
            objectManager.updateObjects();


            //RENDERING
            while (unprocessedTime >= UPDATE_CAP) {
                unprocessedTime -= UPDATE_CAP;
                render = true;
            }
            if (render) {
               // renderer.clear();
                //game.render(this,renderer);
                objectManager.renderObjects();
                renderer.RenderNow();

                window.UpdateWindow();

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

    //Parameters

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
