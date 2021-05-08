package com.apetrei.engine.gui;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.engine.GameContainer;
import com.apetrei.engine.objects.GameObject;
import com.apetrei.engine.objects.ObjectTag;
import com.apetrei.engine.objects.components.PlayerComponent;
import com.apetrei.engine.renderer.ResourceLoader;
import com.apetrei.misc.Vector2;
import com.apetrei.misc.observer.ObjectManagerObserver;
import com.apetrei.misc.observer.PlayerObserver;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class HUDManager implements PlayerObserver, ObjectManagerObserver {

    private GameContainer gameContainer;

    //Variables
    private int engineLevel = 0;
    private int playerHealt = ConfigHandler.getMaxPlayerHealt();

    //Sprites
    ArrayList<BufferedImage> gauge = new ArrayList<BufferedImage>();
   // BufferedImage healt1 = null;
   // BufferedImage healt2 = null;

    public HUDManager(GameContainer gameContainer){
        this.gameContainer = gameContainer;

        try {
            gauge.add(  ResourceLoader.getInstance().getSprite("speed_gauge_0.png")  );
            gauge.add(  ResourceLoader.getInstance().getSprite("speed_gauge_1.png")  );
            gauge.add(  ResourceLoader.getInstance().getSprite("speed_gauge_2.png")  );
            gauge.add(  ResourceLoader.getInstance().getSprite("speed_gauge_3.png")  );
            gauge.add(  ResourceLoader.getInstance().getSprite("speed_gauge_4.png")  );

        //    healt1 = ImageLoader.getInstance().getSprite("healt_top.png");
        //    healt2 = ImageLoader.getInstance().getSprite("healt_bottom.png");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateHUD(){
        Vector2 poz = new Vector2(ConfigHandler.getWidth() * 0.90f  ,ConfigHandler.getHeight() * 0.87f );
        gameContainer.getRenderer().getLayerRenderer().drawStaticSprite( poz, 2f ,gauge.get(engineLevel + 1));

        Vector2 healtCorner =  new Vector2( ConfigHandler.getWidth() * 0.07f,ConfigHandler.getHeight() * 0.05f);

        Vector2 healtSize = new Vector2((float)playerHealt / ConfigHandler.getMaxPlayerHealt() * 270f,30);

        gameContainer.getRenderer().getLayerRenderer().drawFilledRectangle(healtCorner,new Vector2( healtCorner).add(healtSize), Color.red);

       /* Vector2 poz2 = new Vector2(ConfigHandler.getWidth() * 0.30f  ,ConfigHandler.getHeight() * 0.10f );
        gameContainer.getRenderer().getLayerRenderer().drawStaticSprite( poz2, 1f ,healt1);
       Vector2 poz3 = new Vector2(ConfigHandler.getWidth() * 0.05f  ,ConfigHandler.getHeight() * 0.10f );
        gameContainer.getRenderer().getLayerRenderer().drawStaticSprite( poz3, 1f ,healt2);
        */
    }

    //_________________________________OBESERVER___________________________________

    @Override
    public void playerUpdate(int engineLevel , int playerHealt) {
        this.engineLevel = engineLevel;
        this.playerHealt = playerHealt;
    }

    @Override
    public void newObjectUpdate(GameObject gameObject) {

        if(gameObject.hasTag(ObjectTag.player) && gameObject.hasComponent(PlayerComponent.class)){
            try {
                PlayerComponent  pc = (PlayerComponent) gameObject.getComponent(PlayerComponent.class);
                pc.attach(this);
            } catch (Exception e) {
                System.err.println( "");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void objectDeletedUpdate(GameObject gameObject) {

    }
}
