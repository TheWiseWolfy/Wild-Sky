package com.apetrei.engine.scenes;

import com.apetrei.engine.GameContainer;
import com.apetrei.misc.Vector2;

import java.awt.*;

public class MainMenuScene implements Scene{
    @Override
    public void update(GameContainer gameContainer, float frameTime) {

    }

    @Override
    public void render(GameContainer gameContainer) {
        gameContainer.getRenderer().getLayerRenderer().drawRectangle(new Vector2( 0,0 ), new Vector2( 2000,2000), Color.WHITE);
    }
}
