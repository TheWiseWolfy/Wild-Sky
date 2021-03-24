package com.apetrei.engine;

public class GameContainer implements Runnable {

    //Thread pe care va rula enginul
    private Thread thread;
    private Window window;
    private Renderer renderer;
    private Input input;
    private AbstractGame game;

    private  boolean running = false;

    //Propietatile jocului
    private  final double UPDATE_CAP= 1.0/60.0;
    private int width = 640,
                height =480;
    private float scale = 2f;
    private String title = "Engine v1.0";

    public GameContainer(AbstractGame _game){
        this.game = _game;
    }

    public void start(){
        //Initializari importante
        thread = new Thread(this);
        window = new Window(this);
        renderer = new Renderer(this);
        input = new Input(this);

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

            //Un desing interesant dar cu o limitatie majora, si anume ca daca jocul devine mai greu de randat, logica o sa efectueze lucrurile mai incet
            //Intru-un joc ideal ar fi ca logica sa se mentina independenta de viteza cu care este randat

            while (unprocessedTime >= UPDATE_CAP) {
                unprocessedTime -= UPDATE_CAP;
                render = true;                              //Decidem ca e timpul pentru un cadru nou
                //System.out.println("Running");

                game.update(this, (float)UPDATE_CAP);

                //Paint 2 - my greatest creation
               // if(input.isMouseKeyPressed(1)) {
              //      renderer.setPixel((int) (input.getMouseX()), (int) (input.getMouseY()));}

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
                game.render(this,renderer);
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
