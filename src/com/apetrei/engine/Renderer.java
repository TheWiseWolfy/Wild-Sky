package com.apetrei.engine;

import java.awt.*;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;

public class Renderer {

    private int pixelsW, pixelsH;
    private int[] pixels;

    public Renderer(GameContainer gc){
        pixelsW = gc.getWidth();
        pixelsH = gc.getHeight();

        pixels = ((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData();  //O dear god

    }

    public void clear(){

        for (int i = 0; i < pixels.length; i++){
           // pixels[i] = 0xff000000;
            pixels[i] += 0;


        }
    }
}
