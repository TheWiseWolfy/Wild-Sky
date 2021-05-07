package com.apetrei.engine.scenes;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.engine.GameContainer;
import com.apetrei.engine.gui.UIElements.Button;
import com.apetrei.engine.renderer.ImageLoader;
import com.apetrei.misc.Vector2;
import com.apetrei.misc.exceptions.SpriteNotFoundException;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MainMenuScene implements Scene {

    BufferedImage background = null;
    BufferedImage sprite = null;

    public MainMenuScene(GameContainer gameContainer) {

        try {
            sprite = ImageLoader.getInstance().getSprite("Button.png");
            background = ImageLoader.getInstance().getSprite("main_menu_background.jpg");
        } catch (SpriteNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void init(GameContainer gameContainer) {
        gameContainer.getMenuManager().clearUI();

        if (sprite != null) {
            //START GAME BUTTON

            Vector2 button1Poz = new Vector2(ConfigHandler.getWidth() / 2, ConfigHandler.getHeight() / 2 + 100);

            Button button1 = new com.apetrei.engine.gui.UIElements.Button(button1Poz, 0.3f, sprite, () -> {
                gameContainer.goTo(new GameplayScene(gameContainer));
            });

            //SETTINGS BUTTON
            Vector2 button3Poz = new Vector2(ConfigHandler.getWidth() / 2, ConfigHandler.getHeight() / 2 + 200);
            Button button3 = new Button(button3Poz, 0.3f, sprite, () -> {
                System.out.println("wew");
            });

            //CLOSE GAME BUTTON
            Vector2 button2Poz = new Vector2(ConfigHandler.getWidth() / 2, ConfigHandler.getHeight() / 2 + 300);
            Button button2 = new com.apetrei.engine.gui.UIElements.Button(button2Poz, 0.3f, sprite, gameContainer::close);

            gameContainer.getMenuManager().addUIElement(button1);
            gameContainer.getMenuManager().addUIElement(button2);
            gameContainer.getMenuManager().addUIElement(button3);
        }

    }

    @Override
    public void update(GameContainer gameContainer, float frameTime) {
        gameContainer.getMenuManager().update();
    }


    @Override
    public void render(GameContainer gameContainer) {
        gameContainer.getRenderer().getLayerRenderer().drawFilledRectangle(new Vector2(0, 0), new Vector2(2000, 2000), Color.WHITE);
        gameContainer.getRenderer().getLayerRenderer().drawStaticSprite(new Vector2( ConfigHandler.getWidth()/2,ConfigHandler.getHeight()/2), 0.6f, background);
        gameContainer.getMenuManager().draw();
    }
}
