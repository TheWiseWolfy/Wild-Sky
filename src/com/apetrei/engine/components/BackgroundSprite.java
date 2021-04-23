package com.apetrei.engine.components;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BackgroundSprite extends Component {
    private BufferedImage sprite = null;
    private TransformComponent transformComponent;
    private float spriteScale = 1f;


    private float scrollFactor = 1f;

    public BackgroundSprite(String path){
        super();

        try {
            sprite = ImageIO.read(new File(path));
        } catch (IOException e) {
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
        parent.getGameContainer().getRenderer().drawSprite(transformComponent.getPosition(),spriteScale,sprite, scrollFactor);
    }

    //__________________SETTER______________

    public void setSpriteScale(float spriteScale) {
        this.spriteScale = spriteScale;
    }

    public void setScrollFactor(float scrollFactor) {
        this.scrollFactor = scrollFactor;
    }

}
