package com.apetrei.engine.scenes;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.engine.GameContainer;
import com.apetrei.engine.gui.UIElements.Button;
import com.apetrei.engine.input.InputType;
import com.apetrei.engine.renderer.ResourceLoader;
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
            gameContainer.goTo(new GameplayScene(gameContainer));
        });

        //Buttons
        Vector2 button2Poz = new Vector2(ConfigHandler.getWidth() * 0.1f, ConfigHandler.getHeight() * 0.9f  );
        Button button2 = Button.makeButton("Back", button2Poz, 0.3f, gameContainer::goBack);

        gameContainer.getMenuManager().addUIElement(mapButton1);
        gameContainer.getMenuManager().addUIElement(button2);

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
        mapButton1.setPosition(mapButton1Poz);
    }
}
