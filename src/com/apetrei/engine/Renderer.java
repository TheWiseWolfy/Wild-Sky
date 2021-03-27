package com.apetrei.engine;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Renderer {
    private GameContainer gc;
    private Graphics graphics;
    private BufferedImage image;


    private int pixelsW, pixelsH;
    private int[] pixels;

    public Renderer(GameContainer _gameContainer){
        gc = _gameContainer;
        graphics = gc.getWindow().getGraphics();

        pixelsW = ConfigHandler.getWidth();
        pixelsH = ConfigHandler.getHeight();


        image = new BufferedImage(ConfigHandler.getWidth(), ConfigHandler.getHeight(),BufferedImage.TYPE_INT_ARGB);

    }

    public void RenderNow(){

        int realSizeX= (int)(ConfigHandler.getWidth()* ConfigHandler.getScale() );
        int realSizeY= (int)(ConfigHandler.getHeight()* ConfigHandler.getScale() );

        //Sprite
         graphics.clearRect(0,0,realSizeX,realSizeY);
         gc.getObjectManager().renderObjects();

         //Post processing layer
         pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
         clear();
         graphics.drawImage(image,0,0,realSizeX,realSizeY ,null);

         //Final render
         gc.getWindow().UpdateWindow();

    }



    public void setPixel( int x, int y){
        pixels[y * pixelsW + x] = 0xFFFFFFFF;

    }

    public void clear(){
        for (int i = 0; i < pixels.length; i++){
            pixels[i] = 0x00000000;
        }
    }

    public void drawSprite(double x, double y, BufferedImage img){
        graphics.drawImage(img,(int)x,(int)y,200,200,null);
    }
}
