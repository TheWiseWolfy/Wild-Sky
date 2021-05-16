package com.apetrei.engine.sound;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.providers.ResourceLoader;

import javax.sound.sampled.*;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;


public class SoundManager {

    static private SoundManager soundManager;

    private HashMap<String, Clip> activeClips = new HashMap<String, Clip >();

    public void playSound(String soundName) {

        if (!activeClips.containsKey(soundName)) {
            loadClip(soundName);
        }

        Clip clip = activeClips.get(soundName);

        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * ConfigHandler.Volume) + gainControl.getMinimum();
        gainControl.setValue(gain);

        clip.setFramePosition(0);
        clip.start();

    }
    private void loadClip(String soundName){
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream sound = ResourceLoader.getInstance().getSound(soundName);
            clip.open(sound);

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * ConfigHandler.Volume) + gainControl.getMinimum();
            gainControl.setValue(gain);

            activeClips.put(soundName, clip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public float getLenghtOfClip(String soundName){
        if( !activeClips.containsKey(soundName)  ){
            loadClip(soundName);
        }
        return (float)activeClips.get(soundName).getMicrosecondLength() /1000000;
    }

    public void stopSound( String soundName){
        if( activeClips.containsKey(soundName )){
            Clip clip = activeClips.get(soundName);

            clip.stop();
        }
    }

    public void stopAllSound(){
        for (var sound : activeClips.entrySet()) {
            stopSound(sound.getKey());
        }

    }

    //______________________SINGLETON ________________---

    public static SoundManager getInstance(){
        if (soundManager == null) {
            soundManager = new SoundManager();
            return soundManager;
        }
        else {
            return soundManager;
        }
    }
}
