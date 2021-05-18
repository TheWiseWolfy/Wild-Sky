package com.apetrei.engine.scenes;

import com.apetrei.engine.providers.ConfigHandler;
import com.apetrei.engine.GameContainer;
import com.apetrei.engine.event.GlobalEvent;
import com.apetrei.engine.gui.UIElements.Button;
import com.apetrei.engine.gui.UIElements.MapButton;
import com.apetrei.engine.input.InputType;
import com.apetrei.engine.providers.DatabaseManager;
import com.apetrei.engine.providers.ResourceLoader;
import com.apetrei.engine.renderer.CustomFonts;
import com.apetrei.engine.scenes.levels.Level1;
import com.apetrei.engine.scenes.levels.Level2;
import com.apetrei.engine.scenes.levels.Level3;
import com.apetrei.engine.scenes.levels.Level4;
import com.apetrei.misc.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class LevelMenuScene implements Scene {
    GameContainer gameContainer;

    //Interface
    Vector2 currentMousePoz = new Vector2(0,0);
    Vector2 origin = new Vector2(0,0);
    Vector2 moved = new Vector2(0,0);

    Vector2 mapPosition = new Vector2(0,0);
    Vector2 relativeCoordinateSystem = new Vector2(0,0);
    BufferedImage backgroundMap = null;

    float borderX = 200;
    float borderY = 100;

    Button mapButton1;
    Button mapButton2;
    Button mapButton3;
    Button mapButton4;

    public LevelMenuScene( GameContainer gameContainer){

        try {
            backgroundMap = ResourceLoader.getInstance().getSprite("map.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.gameContainer = gameContainer;
    }

    @Override
    public void init() {
        gameContainer.getMenuManager().clearUI();

        //Level logic
        if (  gameContainer.getGlobalEventQueue().didItHappen(GlobalEvent.LEVEL1_COMPLETED)) {
            ConfigHandler.setCurrentLevel( Math.max(2,   ConfigHandler.getCurrentLevel() ) );
        }
        if (  gameContainer.getGlobalEventQueue().didItHappen(GlobalEvent.LEVEL2_COMPLETED)) {
            ConfigHandler.setCurrentLevel( Math.max(3,   ConfigHandler.getCurrentLevel() ) );
        }
        if (  gameContainer.getGlobalEventQueue().didItHappen(GlobalEvent.LEVEL3_COMPLETED)) {
            ConfigHandler.setCurrentLevel( Math.max(4,   ConfigHandler.getCurrentLevel() ) );
        }

        //Save current level
      //  DatabaseManager.getInstance().updateDataBase();

        //BUTTONS
        mapButton1 = MapButton.makeMapButton("Level 1", mapPosition, 0.25f, () -> {
            gameContainer.goTo(new Level1(gameContainer));
        });

        mapButton2 = MapButton.makeMapButton("Level 2", mapPosition, 0.25f, () -> {
            gameContainer.goTo(new Level2(gameContainer));
        });

        mapButton3 = MapButton.makeMapButton("Level 3", mapPosition, 0.25f, () -> {
            gameContainer.goTo(new Level3(gameContainer));
        });

        mapButton4 = MapButton.makeMapButton("Level 4", mapPosition, 0.25f, () -> {
            gameContainer.goTo(new Level4(gameContainer));
        });

        gameContainer.getMenuManager().addUIElement(mapButton1);
        gameContainer.getMenuManager().addUIElement(mapButton2);
        gameContainer.getMenuManager().addUIElement(mapButton3);
        gameContainer.getMenuManager().addUIElement(mapButton4);

        //STATIC BUTTONS
        Vector2 buttonBackPoz = new Vector2(ConfigHandler.getWidth() * 0.1f, ConfigHandler.getHeight() * 0.9f  );
        Button buttonBack = Button.makeButton("Back", buttonBackPoz, 0.3f, gameContainer::goBack);
        gameContainer.getMenuManager().addUIElement(buttonBack);
    }

    @Override
    public void update(float frameTime) {

        //SLIDING MAP
        updateSlidingMap();
        gameContainer.getMenuManager().update();

        //Level unlock
        switch (ConfigHandler.getCurrentLevel()) {
            case 1 ->{
                mapButton2.setActive(false);
                mapButton3.setActive(false);
                mapButton4.setActive(false);
            }
            case 2 ->{
                mapButton2.setActive(true);
                mapButton3.setActive(false);
                mapButton4.setActive(false);
            }
            case 3 ->{
                mapButton2.setActive(true);
                mapButton3.setActive(true);
                mapButton4.setActive(false);
            }
            case 4 ->{
                mapButton2.setActive(true);
                mapButton3.setActive(true);
                mapButton4.setActive(true);
            }
        }

        //COMMANDS
        if (gameContainer.getInput().isKey(KeyEvent.VK_ESCAPE, InputType.DOWN)) {
            gameContainer.goBack();
        }
    }

    @Override
    public void render() {
        gameContainer.getRenderer().getLayerRenderer().drawFilledRectangle( new Vector2( 0,0), new Vector2( ConfigHandler.getWidth() + 400, ConfigHandler.getHeight()+ 200), new Color(182,90,73));
        gameContainer.getRenderer().getLayerRenderer().drawStaticSprite(mapPosition, 1.1f, backgroundMap);
        gameContainer.getMenuManager().draw();

        Vector2 textVolumePoz2 = new Vector2( ConfigHandler.getWidth() * 0.1f, ConfigHandler.getHeight() * 0.8f );
        gameContainer.getRenderer().getTextRenderer().drawText("Score: "+ String.valueOf(ConfigHandler.getScore()) , textVolumePoz2, CustomFonts.SEAGRAM ,35, Color.BLACK);

    }

    public void updateSlidingMap(){
        //Sliding map
        currentMousePoz = new Vector2(gameContainer.getInput().getMouseX(), gameContainer.getInput().getMouseY() );

        if( gameContainer.getInput().isMouseKey( 1, InputType.MOUSE_DOWN)){
            origin = currentMousePoz;
        }

        if( gameContainer.getInput().isMouseKey( 1, InputType.MOUSE_CONTINUOUS)){
            moved = new Vector2( origin). sub( currentMousePoz);
            mapPosition.sub(moved);
            origin = currentMousePoz;
        }

        //Camera border
        if( mapPosition.x  < borderX){
            mapPosition.x = borderX;
        }
        if( mapPosition.y  < borderY){
            mapPosition.y = borderY;
        }
        if( mapPosition.x  > ConfigHandler.getWidth() - borderX){
            mapPosition.x = ConfigHandler.getWidth() - borderX;
        }
        if( mapPosition.y  > ConfigHandler.getHeight() - borderY){
            mapPosition.y= ConfigHandler.getHeight()  - borderY;
        }

        //We also move the buttons
        updateMovingButtons();
    }

    public void updateMovingButtons(){
        relativeCoordinateSystem = new Vector2(mapPosition).sub( new Vector2( backgroundMap.getWidth()/2, backgroundMap.getHeight()/2 ));

        Vector2 mapButton1Poz = new Vector2(relativeCoordinateSystem).add(new Vector2(1430,400));
        Vector2 mapButton2Poz = new Vector2(relativeCoordinateSystem).add(new Vector2(1330,140));
        Vector2 mapButton3Poz = new Vector2(relativeCoordinateSystem).add(new Vector2(1070,60));
        Vector2 mapButton4Poz = new Vector2(relativeCoordinateSystem).add(new Vector2(850,100));

        mapButton1.setPosition(mapButton1Poz);
        mapButton2.setPosition(mapButton2Poz);
        mapButton3.setPosition(mapButton3Poz);
        mapButton4.setPosition(mapButton4Poz);
    }
}
