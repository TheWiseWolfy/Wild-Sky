package com.apetrei.engine.gui;

import com.apetrei.engine.GameContainer;
import com.apetrei.engine.gui.UIElements.Button;
import com.apetrei.engine.gui.UIElements.UIElement;
import com.apetrei.engine.scenes.GameplayScene;
import com.apetrei.misc.Vector2;

import java.util.ArrayList;
import java.util.List;

public class MenuManager {

    protected List<UIElement> uiElements = new ArrayList<UIElement>();

    GameContainer gameContainer;

    public MenuManager(GameContainer gameContainer){
        this.gameContainer = gameContainer;

        uiElements.add (new Button(new Vector2( 50,50), new Vector2( 50,50), ()-> { gameContainer.goTo (new GameplayScene(gameContainer) );} ) );

        uiElements.add (new Button(new Vector2( 150,50), new Vector2( 50,50), ()-> {gameContainer.close(); } ) );
        uiElements.add (new Button(new Vector2( 250,50), new Vector2( 50,50), ()-> {System.out.println( "wew");} ) );
    }

    public void update(){
        for ( var element : uiElements) {
            element.update(gameContainer);
        }

    }

    public void draw(){
        for ( var element : uiElements) {
            element.draw(gameContainer);
        }

    }
}
