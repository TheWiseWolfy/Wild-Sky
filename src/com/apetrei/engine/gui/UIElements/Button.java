package com.apetrei.engine.gui.UIElements;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.engine.GameContainer;
import com.apetrei.engine.input.InputType;
import com.apetrei.engine.renderer.CustomFonts;
import com.apetrei.misc.command.Command;
import com.apetrei.misc.Vector2;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Button extends UIElement {

    Vector2 position;
    Vector2 size;
    float scale;
    Vector2 cornerA;
    Vector2 cornerB;

    BufferedImage sprite;
    String buttonText;

    public Button(String buttonText,Vector2 position,float scale, BufferedImage sprite,Command command) {
        super(command);
        this.position = position;
        this.scale = scale;
        this.sprite = sprite;
        this.buttonText = buttonText;

        size = new Vector2( sprite.getWidth() * scale,sprite.getHeight() * scale );

        cornerA = new Vector2( position.x - size.x/2,position.y - size.y/2  );
        cornerB = new Vector2( position.x + size.x/2,position.y + size.y/2  );
    }

    @Override
    public void update(GameContainer gameContainer) {
        if( isPressed( gameContainer )) {
            command.execute();
        }
    }

    private boolean isPressed(GameContainer gameContainer){
        if( gameContainer.getInput().isMouseKey(MouseEvent.BUTTON1 , InputType.MOUSE_DOWN)  ) {

            int mouseX = gameContainer.getInput().getMouseX();
            int mouseY = gameContainer.getInput().getMouseY();

            if (mouseX > cornerA.x && mouseY > cornerA.y && mouseX < cornerB.x && mouseY < cornerB.y ) {
                return  true;
            }
        }
        return false;
    }

    @Override
    public void draw(GameContainer gameContainer) {

        gameContainer.getRenderer().getLayerRenderer().drawStaticSprite(position,scale,sprite);

        Vector2 textPoz = new Vector2( position);

        gameContainer.getRenderer().getTextRenderer().drawText(buttonText , position, "Serif" ,20, Color.BLACK);

        if(ConfigHandler.isDebugMode()) gameContainer.getRenderer().getLayerRenderer().drawRectangle(cornerA, cornerB, Color.red);
    }


}
