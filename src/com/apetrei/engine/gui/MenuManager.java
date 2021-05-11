package com.apetrei.engine.gui;

import com.apetrei.engine.GameContainer;
import com.apetrei.engine.gui.UIElements.UIElement;
import java.util.ArrayList;
import java.util.List;

public class MenuManager {

    protected List<UIElement> uiElements = new ArrayList<UIElement>();

    GameContainer gameContainer;

    public MenuManager(GameContainer gameContainer){
        this.gameContainer = gameContainer;

    }

    public void addUIElement(UIElement uiElement){
        uiElements.add(uiElement);
    }

    public void clearUI(){
        uiElements.clear();
    }


    public void update(){
        for ( var element : uiElements) {
            element.update(gameContainer);
        }

    }

    public void draw(){
        for (var element : uiElements) {
            element.draw(gameContainer);
        }

    }
}
