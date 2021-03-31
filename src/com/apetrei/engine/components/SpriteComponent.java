package com.apetrei.engine.components;

import com.apetrei.engine.GameObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteComponent  extends Component {
    private BufferedImage sprite = null;
    private TransformComponent transformComponent;

    public SpriteComponent(GameObject gameObject,String path){
        super(gameObject);

        try {
            transformComponent = (TransformComponent) parent.getComponent(TransformComponent.class.getSimpleName());
        }
        catch (Exception e){
           e.printStackTrace();
        }

        try {
            sprite = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void componentUpdate( double fT) {

    }

    @Override
    public void componentRender( ) {
        parent.getGameContainer().getRenderer().drawSprite(transformComponent.getPosition().y,transformComponent.getPosition().x,sprite);

        parent.getGameContainer().getRenderer().drawRectangle(0,0,100,100);

    }


}
