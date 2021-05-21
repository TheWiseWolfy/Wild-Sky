package com.apetrei.engine.scenes;

import com.apetrei.engine.providers.ConfigHandler;
import com.apetrei.engine.GameContainer;
import com.apetrei.engine.gui.UIElements.Button;
import com.apetrei.engine.providers.DatabaseManager;
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

        //Back button
        Vector2 button1Poz = new Vector2(ConfigHandler.getWidth() * 0.1f, ConfigHandler.getHeight() * 0.9f  );
        Button button1 = Button.makeButton("Back", button1Poz, 0.3f, () ->{
            gameContainer.goBack();
            DatabaseManager.getInstance().updateDataBase();
        });
        gameContainer.getMenuManager().addUIElement(button1);

        //Volume
        Vector2 buttonVolumePoz = new Vector2(ConfigHandler.getWidth() * 0.1f, ConfigHandler.getHeight() * 0.15f  );
        Button buttonVolume1 = Button.makeShortButton("-", buttonVolumePoz, 0.3f, ()->{
            ConfigHandler.setSoundVolume(ConfigHandler.getSoundVolume() - 0.1f);
        });

        Vector2 buttonVolumePoz2 = new Vector2(ConfigHandler.getWidth() * 0.3f, ConfigHandler.getHeight() * 0.15f  );
        Button buttonVolume2 = Button.makeShortButton("+", buttonVolumePoz2, 0.3f, () -> {
            ConfigHandler.setSoundVolume(ConfigHandler.getSoundVolume() + 0.1f);
        });

        gameContainer.getMenuManager().addUIElement(buttonVolume1);
        gameContainer.getMenuManager().addUIElement(buttonVolume2);

        //Music volume

        //Volume
        Vector2 buttonMusicVolumePoz = new Vector2(ConfigHandler.getWidth() * 0.1f, ConfigHandler.getHeight() * 0.33f  );
        Button buttonMusicVolume1 = Button.makeShortButton("-", buttonMusicVolumePoz, 0.3f, ()->{
            ConfigHandler.setMusicVolume(ConfigHandler.getMusicVolume() - 0.1f);
        });

        Vector2 buttonMusicVolumePoz2 = new Vector2(ConfigHandler.getWidth() * 0.3f, ConfigHandler.getHeight() * 0.33f  );
        Button buttonMusicVolume2 = Button.makeShortButton("+", buttonMusicVolumePoz2, 0.3f, () -> {
            ConfigHandler.setMusicVolume(ConfigHandler.getMusicVolume() + 0.1f);
        });
        gameContainer.getMenuManager().addUIElement(buttonMusicVolume1);
        gameContainer.getMenuManager().addUIElement(buttonMusicVolume2);


    }

    @Override
    public void update(float frameTime) {

        gameContainer.getMenuManager().update();
    }

    @Override
    public void render() {
        gameContainer.getRenderer().getLayerRenderer().drawFilledRectangle( new Vector2( 0,0), new Vector2( ConfigHandler.getWidth() + 400, ConfigHandler.getHeight()+ 200), new Color(238,183,107));

        //Volume value
        Vector2 textVolumePoz1 = new Vector2( ConfigHandler.getWidth() * 0.2f, ConfigHandler.getHeight() * 0.08f );
        gameContainer.getRenderer().getTextRenderer().drawText( "Volum:" , textVolumePoz1, CustomFonts.SEAGRAM ,30, Color.BLACK);
        Vector2 textVolumePoz2 = new Vector2( ConfigHandler.getWidth() * 0.2f, ConfigHandler.getHeight() * 0.15f );
        gameContainer.getRenderer().getTextRenderer().drawText( String.valueOf(ConfigHandler.getSoundVolume()) , textVolumePoz2, CustomFonts.SEAGRAM ,20, Color.BLACK);

        //Music volume
        Vector2 textMusicVolumePoz1 = new Vector2( ConfigHandler.getWidth() * 0.2f, ConfigHandler.getHeight() * 0.25f );
        gameContainer.getRenderer().getTextRenderer().drawText( "Volum muzica:" , textMusicVolumePoz1, CustomFonts.SEAGRAM ,30, Color.BLACK);
        Vector2 textMusicVolumePoz2 = new Vector2( ConfigHandler.getWidth() * 0.2f, ConfigHandler.getHeight() * 0.33f );
        gameContainer.getRenderer().getTextRenderer().drawText( String.valueOf(ConfigHandler.getMusicVolume()) , textMusicVolumePoz2, CustomFonts.SEAGRAM ,20, Color.BLACK);

        //Credits

        Vector2 textCreditsPoz = new Vector2( ConfigHandler.getWidth() * 0.5f, ConfigHandler.getHeight() * 0.08f );
        Vector2 textCreditsPoz2 = new Vector2( ConfigHandler.getWidth() * 0.8f, ConfigHandler.getHeight() * 0.6f );

        String credits = "Credite: \nDirector - Bogdan Gabriel Apetrei" +
                " \nManager de proiect  - Bogdan Gabriel Apetrei" +
                " \nDesigner Principal - Bogdan Gabriel Apetrei" +
                " \nRoluri:" +
                " \nIulius - Iulian Tanăsache" +
                " \nRadulus -Radu Cornea" +
                " \nReiner Von Wolfgang - Cătălin Toma";
        gameContainer.getRenderer().getTextRenderer().drawTextInABox( credits ,textCreditsPoz,textCreditsPoz2, "Serif",20,Color.BLACK);


        gameContainer.getMenuManager().draw();
    }
}
