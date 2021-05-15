package com.apetrei.engine.renderer;

import com.apetrei.misc.exceptions.ResourceNotFoundException;
import com.apetrei.providers.ResourceLoader;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {

    ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();

    String name;
    int nrFames;

    public Animation(String name, int nrFrames) throws ResourceNotFoundException {
        this.name = name;
        this.nrFames = nrFrames;

        for( int i = 0; i < nrFrames; ++i){
            frames.add(   ResourceLoader.getInstance().getSprite(name + "_"+ i +".png"));
        }
    }

    public BufferedImage getFrame( int index) {
        assert (index >= nrFames || index < 0);
        return frames.get(index);
    }

    public int getNrFames() {
        return nrFames;
    }
}
