package com.apetrei.engine.renderer;

import com.apetrei.engine.exceptions.SpriteNotFoundException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ImageLoader {
    static HashMap<String, BufferedImage> spriteMap = new HashMap<String, BufferedImage>();
    static private ImageLoader imageLoader;
    static private  final File folder = new File("src/com/resources/sprites");


    private ImageLoader(){

        listFilesForFolder(folder);
    }

    public void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {

            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                try  {
                    BufferedImage sprite = ImageIO.read(fileEntry);
                    spriteMap.put(fileEntry.getName(), ImageIO.read(fileEntry));
                    System.out.println( fileEntry.getName() );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    static public  ImageLoader getInstance(){
        if (imageLoader == null) {
            imageLoader = new ImageLoader();
            return  imageLoader;
        }
        else {
            return  imageLoader;
        }
    }

    public BufferedImage getSprite(String name) throws SpriteNotFoundException {
        if(spriteMap.containsKey(name))
            return spriteMap.get(name);
        else throw new SpriteNotFoundException(name);
    }
}
