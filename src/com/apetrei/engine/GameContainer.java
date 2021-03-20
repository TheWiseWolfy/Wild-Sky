package com.apetrei.engine;

public class GameContainer implements Runnable {

    //Thread pe care va rula enginul
    private Thread thread;
    private Window window;
    private Renderer renderer;

    private  boolean running = false;

    //Intervalul minim dintre cadre (fps cap = 60fps)
    private  final double UPDATE_CAP= 1.0/60.0;

    private int width = 640,
                height =480;
    private float scale = 2f;
    private String title = "Engine v1.0";

    public GameContainer(){

    }

    public void start(){
        //Initializari importante
        thread = new Thread(this);
        window = new Window(this);
        renderer = new Renderer(this);

        //Pornim un thread separat
        thread.run();
    }

    public void stop(){

    }

    public static void main(String []args){
        GameContainer gc = new GameContainer();
        gc.start();
    }

    //The Runnable interface should be implemented by any class whose instances are intended to be executed by a thread.
    // The class must define a method of no arguments called run.
    public void run(){

        running = true;
        boolean render = true;

        //Monitorizare performanta
        double firstTime = 0;
        double lastTime = System.nanoTime() / 10.0e8;
        double passedTime = 0;
        double unprocessedTime = 0;
        double frameTime = 0;
        int frames = 0;
        int fps = 0;

        while(running) {
            //Presupunem ca nu trebuie sa redesenam jocul
            render = false;

            firstTime = System.nanoTime() / 10.0e8;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;

            unprocessedTime += passedTime;
            frameTime += passedTime;

            while (unprocessedTime >= UPDATE_CAP) {
                unprocessedTime -= UPDATE_CAP;
                render = true;                              //Decidem ca e timpul pentru un cadru nou
                //System.out.println("Running");

                //Update game

                //Afisam cadre pe secunda
                if (frameTime >= 1.0) {
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                    System.out.println("FPS:" + fps);
                }
            }
            if (render) {
                renderer.clear();
                window.Update();
                frames++;
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
    public Window getWindow() {
        return window;
    }

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
