package com.apetrei.engine.sound;

import com.apetrei.providers.ResourceLoader;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;


public class SoundManager {

    static private SoundManager soundManager;

    private HashMap<String, Clip> activeClips = new HashMap<String, Clip >();

    public void playSound(String soundName) {

        if( activeClips.containsKey(soundName)  ){
            Clip clip = activeClips.get(soundName);
            clip.setFramePosition(0);
            clip.start();
        }
        else {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream sound = ResourceLoader.getInstance().getSound(soundName);
                clip.open(sound);
                clip.setFramePosition(0);
                clip.start();
                activeClips.put(soundName, clip);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
