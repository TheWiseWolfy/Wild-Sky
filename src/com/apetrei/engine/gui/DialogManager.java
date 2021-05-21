package com.apetrei.engine.gui;

import com.apetrei.engine.providers.ConfigHandler;
import com.apetrei.engine.sound.SoundManager;
import com.apetrei.misc.Vector2;
import com.apetrei.engine.GameContainer;
import com.apetrei.engine.providers.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*!
 * Aici ne ocupam de gestionarea si afisarea cererilor de dialog din joc
 */
public class DialogManager {
    GameContainer gameContainer;

    private float lasDialogueTime =0;
    private float timePassed = 0;

    private Queue<DialogLine> dialogueQueue = new LinkedList<>();
    ArrayList<BufferedImage> portraits = new ArrayList<BufferedImage>();

    boolean readyForDialogue = true;

    public DialogManager(GameContainer gameContainer){
        this.gameContainer = gameContainer;
        try {
            portraits.add(  ResourceLoader.getInstance().getSprite("Iulius.png")  );
            portraits.add(  ResourceLoader.getInstance().getSprite("Radulus.png")  );
            portraits.add(  ResourceLoader.getInstance().getSprite("Reiner.png")  );
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(float frameTime){
        timePassed += frameTime;
        playDialogue();
    }

    public void addDialogueLine(DialogLine dialogLine){
        dialogueQueue.add(dialogLine);
        lasDialogueTime = timePassed;
    }

    private void playDialogue(){
        //Daca mai ramane dialog de afisat
        if( !dialogueQueue.isEmpty()) {
            if (readyForDialogue ){
                //Dam play la documentul audio curent
                SoundManager.getInstance().playSound(dialogueQueue.peek().audioFile,false);
                readyForDialogue = false;
            }

            //Dupa de documentul audio s-a terminat, mai pregatim unul si dam semnalul
            if(lasDialogueTime + dialogueQueue.peek().duration < timePassed  ) {
                lasDialogueTime = timePassed;
                readyForDialogue = true;
                dialogueQueue.poll();
            }
        }
    }

    public void resetDialogueQueue(){
        dialogueQueue.clear();
        readyForDialogue = true;
    }

    public boolean isDialogueFinished(){
        if( dialogueQueue.isEmpty()) {
            return true;
        }else return false;
    }

    void displayDialogueBox(){
        if(!isDialogueFinished()) {
            int character = dialogueQueue.peek().character;
            //DIALOGUE BOX
            float portraitScale = 0.12f;
            //Poz of portrait
            Vector2 portraitPoz = new Vector2(ConfigHandler.getWidth() * 0.55f, ConfigHandler.getHeight() * 0.13f);
            //Coltul drept sus al potretului
            Vector2 cornerOfPortrair = new Vector2(
                    (float) portraits.get(character).getWidth() / 2 * portraitScale,
                    (float) -portraits.get(character).getHeight() / 2 * portraitScale);

            //Poz of dialogue box
            Vector2 dialogueCorner = new Vector2(portraitPoz).add(cornerOfPortrair);
            //Size of dialogue box
            Vector2 dialogueSize = new Vector2(450f, portraits.get(character).getHeight() * portraitScale);

            //Draw everything
            gameContainer.getRenderer().getLayerRenderer().drawFilledRectangle(dialogueCorner, new Vector2(dialogueCorner).add(dialogueSize), new Color(238, 183, 107));
            gameContainer.getRenderer().getLayerRenderer().drawStaticSprite(portraitPoz, portraitScale, portraits.get(character));
            gameContainer.getRenderer().getTextRenderer().drawTextInABox(
                    dialogueQueue.peek().dialogLine,
                    dialogueCorner.add(new Vector2(10, 2)),
                    new Vector2(dialogueCorner).add(dialogueSize),
                    "Serif", 22, Color.BLACK);
        }
    }
}
