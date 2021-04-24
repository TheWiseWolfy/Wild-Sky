package com.apetrei.engine.components;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.engine.GameContainer;
import com.apetrei.engine.exceptions.SpriteNotFoundException;
import com.apetrei.engine.renderer.Camera;
import com.apetrei.engine.renderer.ImageLoader;
import com.apetrei.misc.Vector2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/*!
 * Acelasi lucru ca la Background sprite, dar cu suport pentru rotatie in loc de paralax scrolling
 */
public class SpriteComponent  extends Component {

    private float spriteScale = 1f;
    private TransformComponent transformComponent;
    String name;

    public SpriteComponent(String name){
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

        GameContainer.getInstance().getRenderer().drawSprite(  transformComponent.getPosition(),spriteScale ,transformComponent.getRotation(),sprite);
    }

    //____________________________SETTERS_______________

    public void setSpriteScale(float spriteScale) {
        this.spriteScale = spriteScale;
    }

}
