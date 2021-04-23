package com.apetrei.engine.components;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.engine.exceptions.SpriteNotFoundException;
import com.apetrei.engine.renderer.Camera;
import com.apetrei.engine.renderer.ImageLoader;
import com.apetrei.misc.Vector2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteComponent  extends Component {
    private BufferedImage sprite = null;

    private float spriteScale = 1f;
    private TransformComponent transformComponent;

    public SpriteComponent(String name){
        super();

        try {
            sprite = ImageLoader.getInstance().getSprite(name);

        } catch ( SpriteNotFoundException e) {
            e.printStackTrace();
        }
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
        Vector2 destRect = new Vector2();

        parent.getGameContainer().getRenderer().drawSprite(  transformComponent.getPosition(),spriteScale ,transformComponent.getRotation(),sprite);
    }

    //____________________________SETTERS_______________

    public void setSpriteScale(float spriteScale) {
        this.spriteScale = spriteScale;
    }

}
