package com.apetrei.engine.scenes;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.engine.GameContainer;
import com.apetrei.engine.gui.UIElements.Button;
import com.apetrei.engine.input.InputType;
import com.apetrei.engine.renderer.ResourceLoader;
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

    Vector2 currentMousePoz = new Vector2(0,0);
    Vector2 origin = new Vector2(0,0);
    Vector2 moved = new Vector2(0,0);

    Vector2 mapPosition = new Vector2(0,0);
    Vector2 relativeCoordonateSytem = new Vector2(0,0);
    BufferedImage background = null;

    float borderX = 200;
    float borderY = 100;

    Button mapButton1;
    Button mapButton2;
    Button mapButton3;
    Button mapButton4;

    public LevelMenuScene( GameContainer gameContainer){

        try {
            background = ResourceLoader.getInstance().getSprite("map.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.gameContainer = gameContainer;
    }

    @Override
    public void init() {
        gameContainer.getMenuManager().clearUI();

       //Buttons on map

        mapButton1 = Button.makeButton("Level 1", mapPosition, 0.3f, () -> {
            gameContainer.goTo(new Level1(gameContainer));
        });
        mapButton2 = Button.makeButton("Level 2", mapPosition, 0.3f, () -> {
            gameContainer.goTo(new Level2(gameContainer));
        });
        mapButton3 = Button.makeButton("Level 3", mapPosition, 0.3f, () -> {
            gameContainer.goTo(new Level3(gameContainer));
        });
        mapButton4 = Button.makeButton("Level 4", mapPosition, 0.3f, () -> {
            gameContainer.goTo(new Level4(gameContainer));
        });

        gameContainer.getMenuManager().addUIElement(mapButton1);
        gameContainer.getMenuManager().addUIElement(mapButton2);
        gameContainer.getMenuManager().addUIElement(mapButton3);
        gameContainer.getMenuManager().addUIElement(mapButton4);

        //Buttons
        Vector2 buttonBackPoz = new Vector2(ConfigHandler.getWidth() * 0.1f, ConfigHandler.getHeight() * 0.9f  );
        Button buttonBack = Button.makeButton("Back", buttonBackPoz, 0.3f, gameContainer::goBack);

        gameContainer.getMenuManager().addUIElement(buttonBack);

    }

    @Override
    public void update(float frameTime) {

        //SLIDING MAP
        updateMovingButtons();

        gameContainer.getMenuManager().update();

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

        //COMMANDS
        if (gameContainer.getInput().isKey(KeyEvent.VK_ESCAPE, InputType.DOWN)) {
            gameContainer.goBack();
        }
    }

    @Override
    public void render() {
        gameContainer.getRenderer().getLayerRenderer().drawFilledRectangle( new Vector2( 0,0), new Vector2( ConfigHandler.getWidth() + 400, ConfigHandler.getHeight()+ 200), new Color(182,90,73));
        gameContainer.getRenderer().getLayerRenderer().drawStaticSprite(mapPosition, 1.1f, background);
        gameContainer.getMenuManager().draw();
    }

    public void updateMovingButtons(){

        relativeCoordonateSytem = new Vector2(mapPosition).sub( new Vector2( background.getWidth()/2, background.getHeight()/2 ));

        Vector2 mapButton1Poz = new Vector2(relativeCoordonateSytem).add(new Vector2(1400,100));
        Vector2 mapButton2Poz = new Vector2(relativeCoordonateSytem).add(new Vector2(1400,300));
        Vector2 mapButton3Poz = new Vector2(relativeCoordonateSytem).add(new Vector2(1400,500));
        Vector2 mapButton4Poz = new Vector2(relativeCoordonateSytem).add(new Vector2(1400,700));

        mapButton1.setPosition(mapButton1Poz);
        mapButton2.setPosition(mapButton2Poz);
        mapButton3.setPosition(mapButton3Poz);
        mapButton4.setPosition(mapButton4Poz);
    }
}
