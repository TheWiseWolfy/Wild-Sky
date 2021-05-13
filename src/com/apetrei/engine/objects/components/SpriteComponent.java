package com.apetrei.engine.objects.components;

import com.apetrei.misc.exceptions.ResourceNotFoundException;
import com.apetrei.providers.ResourceLoader;

import java.awt.image.BufferedImage;

/*!
 * Acelasi lucru ca la Background sprite, dar cu suport pentru rotatie in loc de paralax scrolling
 */
public class SpriteComponent  extends Component {

    private float spriteScale = 1f;
    private TransformComponent transformComponent;
    String name;

    BufferedImage sprite = null;

    public SpriteComponent(String name){
        super();
        this.name = name;

        try {
            sprite = ResourceLoader.getInstance().getSprite(name);

        } catch ( ResourceNotFoundException e) {
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
        this.getParent().getGameContainer().getRenderer().getLayerRenderer().drawSprite(  transformComponent.getPosition(),spriteScale ,transformComponent.getRotation(),sprite);
    }

    //____________________________SETTERS_______________

    public void setSpriteScale(float spriteScale) {
        this.spriteScale = spriteScale;
    }

}
