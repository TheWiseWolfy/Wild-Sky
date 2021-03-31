package com.apetrei.engine.renderer;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.engine.GameContainer;

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

    public void Render(){

        int realSizeX= (int)(ConfigHandler.getWidth()* ConfigHandler.getScale() );
        int realSizeY= (int)(ConfigHandler.getHeight()* ConfigHandler.getScale() );

        //Sprite
         graphics.clearRect(0,0,realSizeX,realSizeY);

         pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

         gc.getObjectManager().renderObjects();


    }

    public void Display(){
        int realSizeX= (int)(ConfigHandler.getWidth()* ConfigHandler.getScale() );
        int realSizeY= (int)(ConfigHandler.getHeight()* ConfigHandler.getScale() );

        graphics.drawImage(image,0,0,realSizeX,realSizeY ,null);

        //Final render
        gc.getWindow().UpdateWindow();
    }

    public void drawLine(int x1, int y1, int x2, int y2){
        graphics.setColor(Color.BLACK);
        graphics.drawLine(x1,y1 ,x2,y2);
    }

    public void drawLine(int x1, int y1, int x2, int y2,Color col){
        graphics.setColor(col);
        graphics.drawLine(x1,y1 ,x2,y2);
    }

    public void drawRectangle(int x, int y, int wight, int height){
        graphics.setColor(Color.BLACK);
        graphics.drawRect(x,y ,wight,height);
    }

    public void drawRectangle(int x, int y, int wight, int height, Color col){
        graphics.setColor(col);
        graphics.drawRect(x,y ,wight,height);
    }

    public void drawSprite(double x, double y, BufferedImage img){
        graphics.drawImage(img,(int)x,(int)y,200,200,null);
    }

    public void setPixel( int x, int y){
        pixels[y * pixelsW + x] = 0xFF000000;

    }

    public void clear(){
        for (int i = 0; i < pixels.length; i++){
            pixels[i] = 0x00000000;
        }
    }


}
