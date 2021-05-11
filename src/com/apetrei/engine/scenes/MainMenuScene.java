package com.apetrei.engine.scenes;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.engine.GameContainer;
import com.apetrei.engine.gui.UIElements.Button;
import com.apetrei.engine.renderer.CustomFonts;
import com.apetrei.engine.renderer.ResourceLoader;
import com.apetrei.misc.Vector2;
import com.apetrei.misc.exceptions.ResourceNotFoundException;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MainMenuScene implements Scene {
    GameContainer gameContainer;

    BufferedImage background = null;

    public MainMenuScene(GameContainer gameContainer) {
        this.gameContainer = gameContainer;
        try {
            background = ResourceLoader.getInstance().getSprite("main_menu_background.jpg");
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void init() {
        gameContainer.getMenuManager().clearUI();


        //START GAME BUTTON

        Vector2 button1Poz = new Vector2(ConfigHandler.getWidth() / 2, ConfigHandler.getHeight() / 2 );
        Button button1 = Button.makeButton("New Game",button1Poz, 0.3f, () -> {
            gameContainer.goTo(new LevelMenuScene(gameContainer));
        });

        //SETTINGS BUTTON
        Vector2 button2Poz = new Vector2(ConfigHandler.getWidth() / 2, ConfigHandler.getHeight() / 2 + 100);
        Button button2 = Button.makeButton("Continue",button2Poz, 0.3f, () -> {
            System.out.println("trolled");
        });

        //SETTINGS BUTTON
        Vector2 button3Poz = new Vector2(ConfigHandler.getWidth() / 2, ConfigHandler.getHeight() / 2 + 200);
        Button button3 = Button.makeButton("Settings",button3Poz, 0.3f, () -> {
            gameContainer.goTo(new SettingsScene(gameContainer));
        });

        //CLOSE GAME BUTTON
        Vector2 button4Poz = new Vector2(ConfigHandler.getWidth() / 2, ConfigHandler.getHeight() / 2 + 300);
        Button button4 =Button.makeButton("Quit",button4Poz, 0.3f, gameContainer::close);

        gameContainer.getMenuManager().addUIElement(button1);
        gameContainer.getMenuManager().addUIElement(button2);
        gameContainer.getMenuManager().addUIElement(button3);
        gameContainer.getMenuManager().addUIElement(button4);


    }

    @Override
    public void update( float frameTime) {
        gameContainer.getMenuManager().update();
    }

    @Override
    public void render( ) {
        gameContainer.getRenderer().getLayerRenderer().drawStaticSprite(new Vector2( ConfigHandler.getWidth()/2,ConfigHandler.getHeight()/2), 0.6f, background);
        gameContainer.getMenuManager().draw();

        Vector2 textPoz = new Vector2( ConfigHandler.getWidth()/2, 200);
        gameContainer.getRenderer().getTextRenderer().drawText("Wild-Sky" , textPoz, CustomFonts.SEAGRAM ,100, Color.BLACK);

    }
}
