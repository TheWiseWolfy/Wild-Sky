package com.apetrei.engine.gui.UIElements;


import com.apetrei.engine.GameContainer;
import com.apetrei.misc.command.Command;

public abstract class UIElement {

    protected  Command command  = null;

    int press = 0;
    UIElement (Command command){
        this.command = command;
    }

    abstract public void update(GameContainer gameContainer);

    abstract public void draw(GameContainer gameContainer);

}
