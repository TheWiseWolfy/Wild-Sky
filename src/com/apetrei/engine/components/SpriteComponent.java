package com.apetrei.engine.components;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.engine.renderer.Camera;
import com.apetrei.misc.Vector2;

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
        Vector2 destRect = new Vector2();

        parent.getGameContainer().getRenderer().drawSprite(  transformComponent.getPosition(),1f,transformComponent.getRotation(),sprite);
    }
}
