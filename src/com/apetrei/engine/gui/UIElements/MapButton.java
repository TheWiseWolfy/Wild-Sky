package com.apetrei.engine.gui.UIElements;

import com.apetrei.engine.providers.ConfigHandler;
import com.apetrei.engine.GameContainer;
import com.apetrei.engine.providers.ResourceLoader;
import com.apetrei.misc.Vector2;
import com.apetrei.misc.command.Command;
import com.apetrei.misc.exceptions.ResourceNotFoundException;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MapButton extends Button{


    protected MapButton(String buttonText, Vector2 position, float scale, BufferedImage sprite, Command command) {
        super(buttonText, position, scale, sprite, command);
    }

    @Override
    public void draw(GameContainer gameContainer) {
        if(isActive) {
            gameContainer.getRenderer().getLayerRenderer().drawStaticSprite(position, scale, sprite);

            Vector2 textPoz = new Vector2(position);

            gameContainer.getRenderer().getTextRenderer().drawText(buttonText, position.add( new Vector2(0,-50)), "Serif", 30, Color.BLACK);

            if (ConfigHandler.isDebugMode())
                gameContainer.getRenderer().getLayerRenderer().drawRectangle(cornerA, cornerB, Color.red);
        }
    }

    static public MapButton makeMapButton(String buttonText, Vector2 position, float scale, Command command){

        BufferedImage sprite = null;
        try {
            sprite = ResourceLoader.getInstance().getSprite("MapButton.png");

        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        return new MapButton(buttonText,position,scale,sprite,command);
    }
}
