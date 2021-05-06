package com.apetrei.engine.gui.UIElements;

import com.apetrei.engine.GameContainer;
import com.apetrei.engine.input.InputType;
import com.apetrei.misc.command.Command;
import com.apetrei.misc.Vector2;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Button extends UIElement {

    Vector2 position;
    Vector2 size;

    public Button(Vector2 position,Vector2 size,Command command) {
        super(command);
        this.position = position;
        this.size = size;
    }

    @Override
    public void update(GameContainer gameContainer) {
        if( isPressed( gameContainer )) {
            command.execute();
        }
    }

    private boolean isPressed(GameContainer gameContainer){
        if( gameContainer.getInput().isMouseKey(MouseEvent.BUTTON1 , InputType.MOUSE_DOWN)  ) {

            float varX = position.x + size.x ;
            float varY = position.y + size.y ;

            int mouseX = gameContainer.getInput().getMouseX();
            int mouseY = gameContainer.getInput().getMouseY();

            if (mouseX > position.x && mouseX < varX && mouseY > position.y && mouseY < varY) {
                return  true;
            }
        }
        return false;
    }

    @Override
    public void draw(GameContainer gameContainer) {

        gameContainer.getRenderer().getLayerRenderer().drawRectangle(position,new Vector2( position).add(size), Color.red);
    }


}
