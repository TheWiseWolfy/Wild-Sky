package com.apetrei.engine.gui;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.engine.GameContainer;
import com.apetrei.engine.objects.GameObject;
import com.apetrei.engine.objects.ObjectTag;
import com.apetrei.engine.objects.components.GameObjectiveComponent;
import com.apetrei.engine.objects.components.PlayerComponent;
import com.apetrei.providers.ResourceLoader;
import com.apetrei.misc.Vector2;
import com.apetrei.misc.observer.ObjectManagerObserver;
import com.apetrei.misc.observer.ObjectiveObserver;
import com.apetrei.misc.observer.PlayerObserver;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class HUDManager implements PlayerObserver, ObjectiveObserver, ObjectManagerObserver {

    private GameContainer gameContainer;

    //Variables
    private int engineLevel = 0;
    private int playerHealt = ConfigHandler.getMaxPlayerHealt();

    private int objectiveHealth = -1;
    private int maxObjectiveHealth = 10000;

    private float timePassed = 0;
    private float lasDialogueTime =0;

    private String currentDialogLine ="";
    int character;

    private Queue<DialogLine> dialogueQueue = new LinkedList<>();

    //Sprites
    ArrayList<BufferedImage> gauge = new ArrayList<BufferedImage>();
    ArrayList<BufferedImage> portraits = new ArrayList<BufferedImage>();

    public HUDManager(GameContainer gameContainer){
        this.gameContainer = gameContainer;
        try {
            gauge.add(  ResourceLoader.getInstance().getSprite("speed_gauge_0.png")  );
            gauge.add(  ResourceLoader.getInstance().getSprite("speed_gauge_1.png")  );
            gauge.add(  ResourceLoader.getInstance().getSprite("speed_gauge_2.png")  );
            gauge.add(  ResourceLoader.getInstance().getSprite("speed_gauge_3.png")  );
            gauge.add(  ResourceLoader.getInstance().getSprite("speed_gauge_4.png")  );

            portraits.add(  ResourceLoader.getInstance().getSprite("Iulius.png")  );
            portraits.add(  ResourceLoader.getInstance().getSprite("Radulus.png")  );
            portraits.add(  ResourceLoader.getInstance().getSprite("Reiner.png")  );
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateHUD(float fT) {
        timePassed += fT;
        playDialogue();
    }

    public void renderHUD(){
        Vector2 poz = new Vector2(ConfigHandler.getWidth() * 0.90f  ,ConfigHandler.getHeight() * 0.87f );
        gameContainer.getRenderer().getLayerRenderer().drawStaticSprite( poz, 2f ,gauge.get(engineLevel + 1));

        //Player health
        Vector2 healthCorner =  new Vector2( ConfigHandler.getWidth() * 0.07f,ConfigHandler.getHeight() * 0.05f);
        Vector2 healthSize = new Vector2((float)playerHealt / ConfigHandler.getMaxPlayerHealt() * 350f,30);
        gameContainer.getRenderer().getLayerRenderer().drawFilledRectangle(healthCorner,new Vector2( healthCorner).add(healthSize), Color.red);

        //OBJECTIVE HEALT
        if(objectiveHealth != -1){
            //Player health
            Vector2 objectiveHealthCorner =  new Vector2( ConfigHandler.getWidth() * 0.07f,ConfigHandler.getHeight() * 0.9f);
            Vector2 objectiveHealthSize = new Vector2((float)objectiveHealth / maxObjectiveHealth * 500f,30);
            gameContainer.getRenderer().getLayerRenderer().drawFilledRectangle(objectiveHealthCorner,new Vector2( objectiveHealthCorner).add(objectiveHealthSize), Color.red);
        }

        //DIALOGUE
        if(!currentDialogLine.isEmpty()) {
            displayDialogueBox(currentDialogLine, character);
        }
    }

    //______________________________DISPLAY DIALOGUE___________________________

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

    private void displayDialogueBox(String dialogLine, int character ){

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
        gameContainer.getRenderer().getTextRenderer().drawTextInABox( dialogLine,
                dialogueCorner.add (new Vector2(10,2)), new Vector2( dialogueCorner).add(dialogueSize),
                "Serif" ,22, Color.BLACK );
    }
    public boolean isDialogueFinished(){
        if( dialogueQueue.isEmpty()) {
            return true;
        }else return false;
    }


    //_________________________________OBESERVER_____________________________________

    @Override
    public void newObjectUpdate(GameObject gameObject) {

        if(gameObject.hasTag(ObjectTag.player)){
            try {
                PlayerComponent  pc = (PlayerComponent) gameObject.getComponent(PlayerComponent.class);
                pc.attach(this);
            } catch (Exception e) {
                System.err.println( "");
                e.printStackTrace();
            }
        }

        if(gameObject.hasTag(ObjectTag.objective)){
            try {
                GameObjectiveComponent obj = (GameObjectiveComponent) gameObject.getComponent(GameObjectiveComponent.class);
                obj.attach(this);
            } catch (Exception e) {
                System.err.println( "");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void objectDeletedUpdate(GameObject gameObject) {

    }

    @Override
    public void objectiveUpdate(int objectiveHealth) {
        this.objectiveHealth = objectiveHealth;
    }

    @Override
    public void playerUpdate(int engineLevel , int playerHealt) {
        this.engineLevel = engineLevel;
        this.playerHealt = playerHealt;
    }

}
