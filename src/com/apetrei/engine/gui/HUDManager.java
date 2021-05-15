package com.apetrei.engine.gui;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.providers.GameContainer;
import com.apetrei.engine.objects.GameObject;
import com.apetrei.engine.objects.components.GameObjectiveComponent;
import com.apetrei.engine.objects.components.HealthInterface;
import com.apetrei.engine.objects.components.PlayerComponent;
import com.apetrei.providers.ResourceLoader;
import com.apetrei.misc.Vector2;
import com.apetrei.misc.observer.ObjectManagerObserver;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class HUDManager implements ObjectManagerObserver {

    private GameContainer gameContainer;

    //Variables
    private int engineLevel = 0;
    private int playerHealt = 0;
    private int objectiveHealth = -1;
    private int maxObjectiveHealth = 0;

    DialogManager dialogManager;

    PlayerComponent playerComponent;
    HealthInterface objectiveHealtInterface;
    //Sprites
    ArrayList<BufferedImage> gauge = new ArrayList<BufferedImage>();

    public HUDManager(GameContainer gameContainer){
        this.gameContainer = gameContainer;
        try {
            gauge.add(  ResourceLoader.getInstance().getSprite("speed_gauge_0.png")  );
            gauge.add(  ResourceLoader.getInstance().getSprite("speed_gauge_1.png")  );
            gauge.add(  ResourceLoader.getInstance().getSprite("speed_gauge_2.png")  );
            gauge.add(  ResourceLoader.getInstance().getSprite("speed_gauge_3.png")  );
            gauge.add(  ResourceLoader.getInstance().getSprite("speed_gauge_4.png")  );


        }
        catch (Exception e){
            e.printStackTrace();
        }
        dialogManager = new DialogManager(gameContainer);
    }

    public void updateHUD(float fT) {
        if(playerComponent != null){
            playerHealt = ConfigHandler.getMaxPlayerHealt();
            playerHealt = playerComponent.getHealth();
            engineLevel = playerComponent.getEngineLevel();
        }
        if( objectiveHealtInterface != null){
            maxObjectiveHealth = objectiveHealtInterface.getMaxHealth();
            objectiveHealth = objectiveHealtInterface.getHealth();
        }
        dialogManager.update(fT);
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
        if(!dialogManager.isDialogueFinished()) {
            dialogManager.displayDialogueBox();
        }
    }

    //_________________________________OBESERVER_____________________________________

    @Override
    public void newObjectUpdate(GameObject gameObject) {

        if(gameObject.hasComponent(PlayerComponent.class)){
            try {
                 playerComponent = (PlayerComponent) gameObject.getComponent(PlayerComponent.class);
            } catch (Exception e) {
                System.err.println( "");
                e.printStackTrace();
            }
        }

        if(gameObject.hasComponent( GameObjectiveComponent.class)){
            try {
                objectiveHealtInterface = (HealthInterface) gameObject.getComponent(HealthInterface.class);
            } catch (Exception e) {
                System.err.println( "");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void objectDeletedUpdate(GameObject gameObject) {

    }

    public DialogManager getDialogManager() {
        return dialogManager;
    }

}
