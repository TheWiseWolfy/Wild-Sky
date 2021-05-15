package com.apetrei.engine.gui;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.engine.objects.GameObject;
import com.apetrei.misc.Vector2;
import com.apetrei.providers.GameContainer;
import com.apetrei.providers.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class DialogManager {
    GameContainer gameContainer;

    private float lasDialogueTime =0;
    private float timePassed = 0;

    private Queue<DialogLine> dialogueQueue = new LinkedList<>();
    ArrayList<BufferedImage> portraits = new ArrayList<BufferedImage>();

    private String currentDialogLine ="";
    int character;

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
        if( !dialogueQueue.isEmpty()) {

            currentDialogLine = dialogueQueue.peek().dialogLine;
            character = dialogueQueue.peek().character;

            if(lasDialogueTime + dialogueQueue.peek().duration < timePassed  ) {
                lasDialogueTime = timePassed;
                dialogueQueue.poll();
            }
        }else {
            currentDialogLine = "";
        }
    }

    public boolean isDialogueFinished(){
        if( dialogueQueue.isEmpty()) {
            return true;
        }else return false;
    }

    void displayDialogueBox(){
        //DIALOGUE BOX
        float portraitScale = 0.12f;
        //Poz of portrait
        Vector2 portraitPoz = new Vector2(ConfigHandler.getWidth() * 0.55f  ,ConfigHandler.getHeight() *  0.13f );
        //Coltul drept sus al potretului
        Vector2 cornerOfPortrair = new Vector2( (float)portraits.get( character).getWidth()/2 * portraitScale,
                (float) -portraits.get(character).getHeight()/2 *portraitScale);

        //Poz of dialogue box
        Vector2 dialogueCorner =new Vector2( portraitPoz).add( cornerOfPortrair );
        //Size of dialogue box
        Vector2 dialogueSize = new Vector2( 450f,portraits.get( character ).getHeight() *portraitScale);

        gameContainer.getRenderer().getLayerRenderer().drawFilledRectangle(dialogueCorner,new Vector2( dialogueCorner).add(dialogueSize), new Color(238,183,107));
        gameContainer.getRenderer().getLayerRenderer().drawStaticSprite(portraitPoz, portraitScale, portraits.get(character));
        gameContainer.getRenderer().getTextRenderer().drawTextInABox( currentDialogLine, dialogueCorner.add (new Vector2(10,2)), new Vector2( dialogueCorner).add(dialogueSize), "Serif" ,22, Color.BLACK );
    }
}
