package com.apetrei.engine.objects.components;

import com.apetrei.engine.renderer.Animation;
import com.apetrei.misc.exceptions.ResourceNotFoundException;

import java.awt.image.BufferedImage;

/*!
 * Acelasi lucru ca la Background sprite, dar cu suport pentru rotatie in loc de paralax scrolling
 */
public class AnimatedSpriteComponent  extends Component {

    private float spriteScale = 1f;
    private TransformComponent transformComponent;
    private String name;

    private BufferedImage currentFrame = null;
    private int currentFrameNumber = 0;
    private Animation animation;

    private Boolean playing = false;
    private Boolean playingBackwards = false;

    public AnimatedSpriteComponent(String name, int nrFrames){
        super();
        this.name = name;
        try {
            animation = new Animation("Airship", nrFrames);
            currentFrame = animation.getFrame(currentFrameNumber);
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
        if( currentFrameNumber  >= animation.getNrFames()-1){
            playing = false;
        }

        if( currentFrameNumber <= 0){
            playingBackwards = false;
        }

        if(playing){
            currentFrame = animation.getFrame( currentFrameNumber);
            currentFrameNumber++;
        }
       else if( playingBackwards){
            currentFrame = animation.getFrame( currentFrameNumber);
            currentFrameNumber--;
        }

    }

    public void playAnimation(){
        playing = true;
        playingBackwards = false;
    }

    public void playAnimationBackwards(){
        playing = false;
        playingBackwards = true;
    }

    @Override
    public void componentRender( ) {
        this.getParent().getGameContainer().getRenderer().getLayerRenderer().drawRotatedSprite(  transformComponent.getPosition(),spriteScale ,transformComponent.getRotation(), currentFrame);
    }

    //____________________________SETTERS_______________
    public void setSpriteScale(float spriteScale) {
        this.spriteScale = spriteScale;
    }

}
