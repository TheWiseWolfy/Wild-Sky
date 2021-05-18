package com.apetrei.engine.renderer;

import com.apetrei.engine.GameContainer;

import java.awt.*;

public class Renderer {
    
    private GameContainer gameContainer;
    private Graphics graphics;

    //Layers of the game
   // BufferedImage gameFrame;
   // BufferedImage HUDLayer;

    //Aici stocam pozitia camerei
    private final Camera camera;

    //Renderers for diffrent layers of the game
    private final LayerRenderer layerRenderer;
    private final TextRenderer textRenderer;

    Graphics graphicsBuffer;

    public Renderer(GameContainer gameContainer){
        this.gameContainer = gameContainer;

        camera = new Camera(gameContainer);

        graphicsBuffer = this.gameContainer.getWindow().getBufferStrategy().getDrawGraphics();
      //  gameFrame = new BufferedImage(ConfigHandler.getWidth(), ConfigHandler.getHeight(),BufferedImage.TYPE_INT_ARGB);
      //  HUDLayer = new BufferedImage(ConfigHandler.getWidth(), ConfigHandler.getHeight(),BufferedImage.TYPE_INT_ARGB);

        layerRenderer = new LayerRenderer(graphicsBuffer ,camera);
        textRenderer = new TextRenderer( graphicsBuffer);
    }

    public void PrepareRender() {

        graphicsBuffer = this.gameContainer.getWindow().getBufferStrategy().getDrawGraphics();
        //  graphics = gameFrame.getGraphics();

       // int realSizeX= (int)(ConfigHandler.getWidth()* ConfigHandler.getScale() );
        //int realSizeY= (int)(ConfigHandler.getHeight()* ConfigHandler.getScale() );
        camera.update();
        layerRenderer.setGraphics( graphicsBuffer);
        textRenderer.setGraphics( graphicsBuffer);
    }

    public void Render(){
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

    public TextRenderer getTextRenderer() {
        return textRenderer;
    }

}
