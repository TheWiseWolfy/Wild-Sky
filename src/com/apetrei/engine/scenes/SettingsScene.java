package com.apetrei.engine.scenes;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.providers.GameContainer;
import com.apetrei.engine.gui.UIElements.Button;
import com.apetrei.engine.renderer.CustomFonts;
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

        //Volume
        Vector2 buttonVolumePoz = new Vector2(ConfigHandler.getWidth() * 0.1f, ConfigHandler.getHeight() * 0.15f  );
        Button buttonVolume1 = Button.makeShortButton("-", buttonVolumePoz, 0.3f, ()->{
            ConfigHandler.setVolume(ConfigHandler.getVolume() - 0.1f);
        });

        Vector2 buttonVolumePoz2 = new Vector2(ConfigHandler.getWidth() * 0.3f, ConfigHandler.getHeight() * 0.15f  );
        Button buttonVolume2 = Button.makeShortButton("+", buttonVolumePoz2, 0.3f, () -> {
            ConfigHandler.setVolume(ConfigHandler.getVolume() + 0.1f);
        });

        gameContainer.getMenuManager().addUIElement(button1);
        gameContainer.getMenuManager().addUIElement(buttonVolume1);
        gameContainer.getMenuManager().addUIElement(buttonVolume2);

    }

    @Override
    public void update(float frameTime) {

        gameContainer.getMenuManager().update();
    }

    @Override
    public void render() {
        gameContainer.getRenderer().getLayerRenderer().drawFilledRectangle( new Vector2( 0,0), new Vector2( ConfigHandler.getWidth() + 400, ConfigHandler.getHeight()+ 200), new Color(238,183,107));

        Vector2 textVolumePoz1 = new Vector2( ConfigHandler.getWidth() * 0.2f, ConfigHandler.getHeight() * 0.08f );
        gameContainer.getRenderer().getTextRenderer().drawText( "Volum:" , textVolumePoz1, CustomFonts.SEAGRAM ,30, Color.BLACK);
        //Volume value
        Vector2 textVolumePoz2 = new Vector2( ConfigHandler.getWidth() * 0.2f, ConfigHandler.getHeight() * 0.15f );
        gameContainer.getRenderer().getTextRenderer().drawText( String.valueOf(ConfigHandler.getVolume()) , textVolumePoz2, CustomFonts.SEAGRAM ,20, Color.BLACK);

        gameContainer.getMenuManager().draw();
    }
}
