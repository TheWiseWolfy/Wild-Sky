package com.apetrei.engine.scenes;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.engine.GameContainer;
import com.apetrei.engine.gui.UIElements.Button;
import com.apetrei.misc.Vector2;

import java.awt.*;

public class SettingsScene implements Scene{

    GameContainer gameContainer;

    SettingsScene(GameContainer gameContainer){
        this.gameContainer = gameContainer;
    }

    @Override
    public void init() {
        gameContainer.getMenuManager().clearUI();

        //Buttons
        Vector2 button1Poz = new Vector2(ConfigHandler.getWidth() * 0.1f, ConfigHandler.getHeight() * 0.9f  );
        Button button1 = Button.makeButton("Back", button1Poz, 0.3f, gameContainer::goBack);

        gameContainer.getMenuManager().addUIElement(button1);
    }

    @Override
    public void update(float frameTime) {

        gameContainer.getMenuManager().update();
    }

    @Override
    public void render() {
        gameContainer.getRenderer().getLayerRenderer().drawFilledRectangle( new Vector2( 0,0), new Vector2( ConfigHandler.getWidth() + 400, ConfigHandler.getHeight()+ 200), new Color(182,90,73));

        gameContainer.getMenuManager().draw();
    }
}
