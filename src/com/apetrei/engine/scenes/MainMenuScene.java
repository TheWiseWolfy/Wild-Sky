package com.apetrei.engine.scenes;

import com.apetrei.engine.providers.ConfigHandler;
import com.apetrei.engine.GameContainer;
import com.apetrei.engine.gui.UIElements.Button;
import com.apetrei.engine.renderer.CustomFonts;
import com.apetrei.engine.sound.SoundManager;
import com.apetrei.engine.providers.DatabaseManager;
import com.apetrei.engine.providers.ResourceLoader;
import com.apetrei.misc.Vector2;
import com.apetrei.misc.exceptions.ResourceNotFoundException;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MainMenuScene implements Scene {
    private GameContainer gameContainer;

    private BufferedImage background = null;
    private BufferedImage warningMenuBackground = null;
    private boolean isWorningOn = false;

    Button button1;
    Button button2;
    Button button5;
    Button button6;

    public MainMenuScene(GameContainer gameContainer) {
        this.gameContainer = gameContainer;
        try {
            background = ResourceLoader.getInstance().getSprite("main_menu_background.jpg");
            warningMenuBackground = ResourceLoader.getInstance().getSprite("warning_menu_background.png");
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        gameContainer.getMenuManager().clearUI();
        //START GAME BUTTON
        Vector2 button1Poz = new Vector2(ConfigHandler.getWidth() / 2, ConfigHandler.getHeight() / 2 );
        button1 = Button.makeButton("Joc nou",button1Poz, 0.3f, () -> {
            isWorningOn = true;
        });

        //SETTINGS BUTTON
        Vector2 button2Poz = new Vector2(ConfigHandler.getWidth() / 2, ConfigHandler.getHeight() / 2 + 100);
        button2 = Button.makeButton("Continuă",button2Poz, 0.3f, () -> {
            gameContainer.goTo(new LevelMenuScene(gameContainer));
        });

        //SETTINGS BUTTON
        Vector2 button3Poz = new Vector2(ConfigHandler.getWidth() / 2, ConfigHandler.getHeight() / 2 + 200);
        Button button3 = Button.makeButton("Setări",button3Poz, 0.3f, () -> {
            gameContainer.goTo(new SettingsScene(gameContainer));
        });

        //CLOSE GAME BUTTON
        Vector2 button4Poz = new Vector2(ConfigHandler.getWidth() / 2, ConfigHandler.getHeight() / 2 + 300);
        Button button4 =Button.makeButton("Închide jocul",button4Poz, 0.3f, gameContainer::close);

        //WARNING MENU
        //CLOSE GAME BUTTON
        Vector2 button5Poz = new Vector2(ConfigHandler.getWidth() / 2 - 100 , ConfigHandler.getHeight() / 2 );
        button5 =Button.makeButton("Joc nou",button5Poz, 0.3f, () -> {
            DatabaseManager.getInstance().resetGameState();
            gameContainer.getGlobalEventQueue().resetHistory();
            gameContainer.goTo(new LevelMenuScene(gameContainer));
        });  //CLOSE GAME BUTTON

        Vector2 button6Poz = new Vector2(ConfigHandler.getWidth() / 2 + 100, ConfigHandler.getHeight() / 2 );
        button6 =Button.makeButton("Abandonează",button6Poz, 0.3f, () -> {
            isWorningOn = false;
        });

        gameContainer.getMenuManager().addUIElement(button1);
        gameContainer.getMenuManager().addUIElement(button2);
        gameContainer.getMenuManager().addUIElement(button3);
        gameContainer.getMenuManager().addUIElement(button4);

        gameContainer.getMenuManager().addUIElement(button5);
        gameContainer.getMenuManager().addUIElement(button6);

        //SOUND
        SoundManager.getInstance().stopAllSound();
        SoundManager.getInstance().playMusic("music2.wav");
        isWorningOn = false;
    }

    @Override
    public void update( float frameTime) {
        gameContainer.getMenuManager().update();

        if (isWorningOn ){
            button1.setActive(false);
            button2.setActive(false);
            button5.setActive(true);
            button6.setActive(true);
        }else {
            button1.setActive(true);
            button2.setActive(true);
            button5.setActive(false);
            button6.setActive(false);
        }
    }

    @Override
    public void render( ) {
        gameContainer.getRenderer().getLayerRenderer().drawStaticSprite(new Vector2( ConfigHandler.getWidth()/2,ConfigHandler.getHeight()/2), 0.6f, background);
        Vector2 textPoz = new Vector2( ConfigHandler.getWidth()/2, 200);
        gameContainer.getRenderer().getTextRenderer().drawText("Wild-Sky" , textPoz, CustomFonts.SEAGRAM ,100, Color.BLACK);

        if(isWorningOn){
            gameContainer.getRenderer().getLayerRenderer().drawStaticSprite(new Vector2( ConfigHandler.getWidth()/2,ConfigHandler.getHeight()/2.15f), 0.6f, warningMenuBackground);

            Vector2 textPoz2 = new Vector2( ConfigHandler.getWidth()/2, 300);
            gameContainer.getRenderer().getTextRenderer().drawText("Esti sigur ca vrei sa iți resetezi progressul ?" , textPoz2, "Serif" ,20, Color.BLACK);
        }
        gameContainer.getMenuManager().draw();

    }
}
