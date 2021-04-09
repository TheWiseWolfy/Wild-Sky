package com.apetrei.engine.components;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteComponent  extends Component {
    private BufferedImage sprite = null;
    private TransformComponent transformComponent;

    public SpriteComponent(String path){
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
        parent.getGameContainer().getRenderer().drawSprite(transformComponent.getPosition().x,transformComponent.getPosition().y,1f,transformComponent.getRotation(),sprite);

            //  parent.getGameContainer().getRenderer().drawSprite(transformComponent.getPosition().x,transformComponent.getPosition().y,sprite);
    }
}
