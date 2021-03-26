package com.apetrei.engine;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
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

        pixelsW = gc.getWidth();
        pixelsH = gc.getHeight();


        image = new BufferedImage(gc.getWidth(),gc.getHeight(),BufferedImage.TYPE_INT_ARGB);

    }

    public void RenderNow(){
         pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();  //O dear god

         graphics.drawImage(image,0,0,(int)(gc.getWidth()* gc.getScale()),(int)(gc.getHeight()* gc.getScale()),null);

    }



    public void setPixel( int x, int y){
        pixels[y * pixelsW + x] = 0xFFFFFFFF;

    }

    public void clear(){
        for (int i = 0; i < pixels.length; i++){
            pixels[i] = 0xff000000;
        }
    }

    public void drawSprite(double x, double y, BufferedImage img){
        graphics.drawImage(img,(int)x,(int)y,200,200,null);
    }
}
