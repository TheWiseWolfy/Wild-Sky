package com.apetrei.engine.gui.UIElements;


import com.apetrei.engine.GameContainer;
import com.apetrei.misc.command.Command;

public abstract class UIElement {

    protected boolean isActive = true;
    protected boolean isInteractive = true;

    protected  Command command  = null;

    UIElement (Command command){
        this.command = command;
    }

    abstract public void update(GameContainer gameContainer);

    abstract public void draw(GameContainer gameContainer);

    public void setActive(Boolean isActive){
        this.isActive = isActive;
    }
    public void setnteractivee(Boolean isInteractive){
        this.isInteractive = isInteractive;
    }
}
