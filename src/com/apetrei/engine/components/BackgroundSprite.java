package com.apetrei.engine.components;

import com.apetrei.engine.GameContainer;
import com.apetrei.engine.exceptions.SpriteNotFoundException;
import com.apetrei.engine.renderer.ImageLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BackgroundSprite extends Component {
    private TransformComponent transformComponent;
    private float spriteScale = 1f;
    String name;

    private float scrollFactor = 1f;

    public BackgroundSprite(String name){
        super();
        this.name = name;

    }

    @Override
    public void componentInit() {

        try {
            transformComponent = (TransformComponent) parent.getComponent(TransformComponent.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void componentUpdate( double fT) {
    }

    @Override
    public void componentRender( ) {
        BufferedImage sprite = null;
        try {
            sprite = ImageLoader.getInstance().getSprite(name);

        } catch ( SpriteNotFoundException e) {
            e.printStackTrace();
        }
        GameContainer.getInstance().getRenderer().drawSprite(transformComponent.getPosition(),spriteScale,sprite, scrollFactor);
    }

    //__________________SETTER______________

    public void setSpriteScale(float spriteScale) {
        this.spriteScale = spriteScale;
    }

    public void setScrollFactor(float scrollFactor) {
        this.scrollFactor = scrollFactor;
    }

}
