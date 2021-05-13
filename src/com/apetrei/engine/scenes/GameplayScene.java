package com.apetrei.engine.scenes;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.engine.GameContainer;
import com.apetrei.engine.gui.UIElements.Button;
import com.apetrei.engine.input.InputType;
import com.apetrei.engine.sound.SoundManager;
import com.apetrei.providers.ResourceLoader;
import com.apetrei.misc.Vector2;
import com.apetrei.misc.exceptions.ResourceNotFoundException;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;


public abstract class GameplayScene implements Scene {
    protected GameContainer gameContainer;

    boolean paused = false;
    BufferedImage pauseMenuBackground = null;

    public GameplayScene(GameContainer gameContainer) {
        this.gameContainer = gameContainer;

        try {
            pauseMenuBackground = ResourceLoader.getInstance().getSprite("Pause_menu_background.png");
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        gameContainer.getMenuManager().clearUI();
        gameContainer.getPhysicsSystem().resetPhysicsSystem();
        gameContainer.getObjectManager().resetObjectManager();

        SoundManager.getInstance().stopAllSound();
        initializePauseMenu(gameContainer);
    }

    @Override
    public void update( float frameTime) {

        if( ConfigHandler.isDebugMode()) {
            if (gameContainer.getInput().isKey(KeyEvent.VK_F1, InputType.DOWN)) {
                gameContainer.goBack();
            }
        }

        if (gameContainer.getInput().isKey(KeyEvent.VK_ESCAPE, InputType.DOWN)) {
            paused = !paused;
            if( ConfigHandler.isDebugMode()) {
                if (paused) {
                    System.out.println("The game has been paused.");
                } else if (!paused) {
                    System.out.println("The game has been unpaused.");
                }
            }
        }

        //PAUSE MENU AND MAIN LOOP
        if (!paused) {
            //PHYSICS UPDATE
            gameContainer.getPhysicsSystem().updatePhysics(frameTime);
            //UPDATE
            gameContainer.getObjectManager().updateObjects(frameTime);
            //HUD UPDATE
            gameContainer.getHudManager().updateHUD(frameTime);
        }else {
            gameContainer.getMenuManager().update();
        }
    }

    @Override
    public void render() {
        if (!paused) {
            gameContainer.getObjectManager().renderObjects();
            gameContainer.getHudManager().renderHUD();
        }else {
            gameContainer.getRenderer().getLayerRenderer().drawStaticSprite(new Vector2( ConfigHandler.getWidth()/2,ConfigHandler.getHeight()/2), 0.6f, pauseMenuBackground);
            gameContainer.getMenuManager().draw();
        }
    }

    private void initializePauseMenu(GameContainer gameContainer){

        //MENU BUTTON
        Vector2 button2Poz = new Vector2(ConfigHandler.getWidth() / 2, ConfigHandler.getHeight() / 2 -100);
        Button button2 =  Button.makeButton("Continue",button2Poz, 0.3f, () -> {
            paused = false;
        });

        //MENU BUTTON
        Vector2 button1Poz = new Vector2(ConfigHandler.getWidth() / 2, ConfigHandler.getHeight() / 2 );
        Button button1 = Button.makeButton("Menu",button1Poz, 0.3f, () -> {
            gameContainer.goBack();
        });

        //SETTINGS BUTTON
        Vector2 button3Poz = new Vector2(ConfigHandler.getWidth() / 2, ConfigHandler.getHeight() / 2 + 100);
        Button button3 = Button.makeButton("Settings",button3Poz, 0.3f, () -> {
            System.out.println("Settings Button");
        });

        gameContainer.getMenuManager().addUIElement(button1);
        gameContainer.getMenuManager().addUIElement(button2);
        gameContainer.getMenuManager().addUIElement(button3);
    }
}
