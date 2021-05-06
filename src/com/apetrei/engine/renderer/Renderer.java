package com.apetrei.engine.renderer;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.engine.GameContainer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Renderer {
    
    private GameContainer gameContainer;
    private Graphics graphics;

    //Layers of the game
    BufferedImage gameFrame;
    BufferedImage HUDLayer;

    //Aici stocam pozitia camerei
    private Camera camera;

    //Renderers for diffrent layers of the game
    private LayerRenderer layerRenderer;

    Graphics graphicsBuffer = null;

    public Renderer(GameContainer gameContainer){
        this.gameContainer = gameContainer;

        camera = new Camera();

        gameFrame = new BufferedImage(ConfigHandler.getWidth(), ConfigHandler.getHeight(),BufferedImage.TYPE_INT_ARGB);
        HUDLayer = new BufferedImage(ConfigHandler.getWidth(), ConfigHandler.getHeight(),BufferedImage.TYPE_INT_ARGB);

        layerRenderer = new LayerRenderer(gameFrame ,camera);
    }

    public void PrepareRender() {

        graphicsBuffer = this.gameContainer.getWindow().getBufferStrategy().getDrawGraphics();
        //  graphics = gameFrame.getGraphics();

        int realSizeX= (int)(ConfigHandler.getWidth()* ConfigHandler.getScale() );
        int realSizeY= (int)(ConfigHandler.getHeight()* ConfigHandler.getScale() );

        layerRenderer.setGraphics( graphicsBuffer);
    }

    public void Render(){


      //  graphicsBuffer.drawImage(gameFrame,0,0,realSizeX,realSizeY ,null);
     //   graphicsBuffer.drawImage(HUDLayer,0,0,realSizeX,realSizeY ,null);


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


}
