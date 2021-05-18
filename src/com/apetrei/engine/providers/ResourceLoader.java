package com.apetrei.engine.providers;

import com.apetrei.misc.exceptions.ResourceNotFoundException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ResourceLoader {

    HashMap<String, BufferedImage> spriteMap = new HashMap<String, BufferedImage>();
    HashMap<String, AudioInputStream> soundMap = new HashMap<String, AudioInputStream >();

    static private ResourceLoader resourceLoader;

     private final File spriteFolder = new File("src/com/resources/sprites");
     private final File fontFolder = new File("src/com/resources/fonts");
    private final File soundsFolder = new File("src/com/resources/sound");

    GraphicsEnvironment graphicsEnvironment;

    private ResourceLoader(){

        try {
            graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        } catch (Exception e) {
            e.printStackTrace();
        }

        listImagesForFolder(spriteFolder);
        listFontsForFolder(fontFolder);
        listSoundsForFolder(soundsFolder);
    }

    public void listImagesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {

            if (fileEntry.isDirectory()) {
                listImagesForFolder(fileEntry);
            } else {
                try  {
                    BufferedImage sprite = ImageIO.read(fileEntry);
                    spriteMap.put(fileEntry.getName(), sprite );

                    if( spriteMap.get( fileEntry.getName() ) == null){
                        System.err.println( fileEntry.getName() + " sprite failed to load properly. It might not be a supported file.");
                    }else {
                        System.out.println(fileEntry.getName() + " sprite has loaded properly.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void listFontsForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {

            if (fileEntry.isDirectory()) {
                listFontsForFolder(fileEntry);
            } else {
                try  {
                    Font myFont =  Font.createFont(Font.TRUETYPE_FONT, fileEntry);
                    graphicsEnvironment.registerFont(myFont);

                    if( myFont  == null){
                        System.err.println( fileEntry.getName() + " font failed to load properly. It might not be a supported file.");
                    }else {
                        System.out.println(myFont.getName() + " font has loaded properly.");
                    }
                } catch (FontFormatException | IOException e) {
                    System.err.println("Font" + fileEntry.getName() + "has failed to load.");
                    e.printStackTrace();
                }
            }
        }
    }

    public void listSoundsForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {

            if (fileEntry.isDirectory()) {
                listSoundsForFolder(fileEntry);
            } else {
                try  {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileEntry);

                    soundMap.put(fileEntry.getName(), audioInputStream );

                    if( soundMap.get( fileEntry.getName() ) == null){
                        System.err.println( fileEntry.getName() + " sprite failed to load properly. It might not be a supported file.");
                    }else {
                        System.out.println(fileEntry.getName() + " sprite has loaded properly.");
                    }
                } catch (IOException | UnsupportedAudioFileException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    //______________________GETTER_________________


    public BufferedImage getSprite(String name) throws ResourceNotFoundException {
        if(spriteMap.containsKey(name))
            return spriteMap.get(name);
        else throw new ResourceNotFoundException(name);
    }

    public AudioInputStream getSound(String name) throws ResourceNotFoundException {
        if(soundMap.containsKey(name))
            return soundMap.get(name);
        else throw new ResourceNotFoundException(name);
    }

    //_______________________Singleton_______________
    static public ResourceLoader getInstance(){
        if (resourceLoader == null) {
            resourceLoader = new ResourceLoader();
            return resourceLoader;
        }
        else {
            return resourceLoader;
        }
    }

}
