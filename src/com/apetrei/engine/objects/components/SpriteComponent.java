package com.apetrei.engine.objects.components;

import com.apetrei.engine.renderer.LayerRenderer;
import com.apetrei.misc.ExtraMath;
import com.apetrei.misc.exceptions.ResourceNotFoundException;
import com.apetrei.engine.providers.ResourceLoader;

import java.awt.image.BufferedImage;

/*!
 * Acelasi lucru ca la Background sprite, dar cu suport pentru rotatie in loc de paralax scrolling
 */
public class SpriteComponent  extends Component {

    private float spriteScale = 1f;
    private TransformComponent transformComponent;
    String name;

    BufferedImage sprite = null;
    BufferedImage rotatedSprite;
    float oldRotation = 0;

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
        rotatedSprite = sprite;

    }
    @Override
    public void componentUpdate( double fT) {

    }

    @Override
    public void componentRender( ) {
        if( ! ExtraMath.equal( transformComponent.rotation ,oldRotation)   ){
            rotatedSprite = LayerRenderer.rotate(sprite,transformComponent.getRotation(), (float)Math.PI /2);
            oldRotation =  transformComponent.rotation;
        }

        this.getParent().getGameContainer().getRenderer().getLayerRenderer().drawSprite(  transformComponent.getPosition(),spriteScale,rotatedSprite);
    }

    //____________________________SETTERS_______________

    public void setSpriteScale(float spriteScale) {
        this.spriteScale = spriteScale;
    }

}
