package com.apetrei.engine.renderer;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.engine.GameContainer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Renderer implements Runnable {
    
    private GameContainer gameContainer;
    private Graphics graphics;

    //Layers of the game
    BufferedImage gameFrame;
    BufferedImage HUDLayer;

    //Aici stocam pozitia camerei
    private Camera camera;
    private LayerRenderer layerRenderer;
    private LayerRenderer hudRenderer;


    public Renderer(GameContainer gameContainer){
        this.gameContainer = gameContainer;

        camera = new Camera();

        gameFrame = new BufferedImage(ConfigHandler.getWidth(), ConfigHandler.getHeight(),BufferedImage.TYPE_INT_ARGB);
        HUDLayer = new BufferedImage(ConfigHandler.getWidth(), ConfigHandler.getHeight(),BufferedImage.TYPE_INT_ARGB);

        layerRenderer = new LayerRenderer(gameFrame ,camera);
        hudRenderer = new LayerRenderer(HUDLayer ,camera);
    }

    public void Render(){

        Graphics graphicsBuffer = this.gameContainer.getWindow().getBufferStrategy().getDrawGraphics();
        graphics = gameFrame.getGraphics();

        int realSizeX= (int)(ConfigHandler.getWidth()* ConfigHandler.getScale() );
        int realSizeY= (int)(ConfigHandler.getHeight()* ConfigHandler.getScale() );

        graphicsBuffer.drawImage(gameFrame,0,0,realSizeX,realSizeY ,null);
        graphicsBuffer.drawImage(HUDLayer,0,0,realSizeX,realSizeY ,null);

        //Rendering step
        gameContainer.getObjectManager().renderObjects();

        //Final render
        graphicsBuffer.dispose();

        this.gameContainer.getWindow().getBufferStrategy().show();
    }



    //Fuctions operating on pixel array

    /*
    public void setPixel( int x, int y){
        pixels[y * pixelsW + x] = 0xFF000000;
    }

    public void clear(){
        for (int i = 0; i < pixels.length; i++){
            pixels[i] = 0x00000000;
        }
    }

    */

    //___________________________________GETTER_______________________________

    public Camera getCamera() {
        return camera;
    }

    public LayerRenderer getLayerRenderer() {
        return layerRenderer;
    }

    public LayerRenderer getHudRenderer() {
        return hudRenderer;
    }

    @Override
    public void run() {
        while (true){

            Graphics graphicsBuffer = this.gameContainer.getWindow().getBufferStrategy().getDrawGraphics();
            graphics = gameFrame.getGraphics();

            int realSizeX= (int)(ConfigHandler.getWidth()* ConfigHandler.getScale() );
            int realSizeY= (int)(ConfigHandler.getHeight()* ConfigHandler.getScale() );

            graphicsBuffer.drawImage(gameFrame,0,0,realSizeX,realSizeY ,null);
            graphicsBuffer.drawImage(HUDLayer,0,0,realSizeX,realSizeY ,null);

            //Rendering step
           // gameContainer.getObjectManager().renderObjects();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Final render
            graphicsBuffer.dispose();

            this.gameContainer.getWindow().getBufferStrategy().show();
        }
    }
}
