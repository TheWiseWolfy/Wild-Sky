package com.apetrei.engine.components;

import com.apetrei.engine.Component;
import com.apetrei.engine.GameContainer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteComponent  extends Component {
    BufferedImage sprite = null;
    TransformComponent transformComponent;

    public SpriteComponent(GameContainer gameContainer,TransformComponent _transformComponent,String path){
        super(gameContainer);
        transformComponent = _transformComponent;
        try {
            sprite = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void componentUpdate(GameContainer gameContainer) {

    }

    @Override
    public void componentRender(GameContainer gameContainer) {
        gameContainer.getRenderer().drawSprite(transformComponent.getX(),transformComponent.getY(),sprite);
    }
}
