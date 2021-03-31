package com.apetrei.engine.components;

import com.apetrei.engine.*;
import com.apetrei.engine.physics.primitives.Box2D;
import com.apetrei.engine.physics.rigidbody.IntersectionDetector2D;
import com.apetrei.misc.Vector2;

import java.awt.*;

public class PlayerComponent extends Component {
    private TransformComponent transformComponent;

    public PlayerComponent(GameObject gameObject){
        super(gameObject);


        try {
            transformComponent = (TransformComponent) parent.getComponent(TransformComponent.class.getSimpleName());
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void componentUpdate(double fT) {

        float mouseX =  parent.getGameContainer().getInput().getMouseY() * ConfigHandler.getScale() ;
        float mouseY = parent.getGameContainer().getInput().getMouseX()* ConfigHandler.getScale();
        if(parent.getGameContainer().getInput().isMouseKeyPressed(1)){
            transformComponent.setPosition(new Vector2(mouseX,mouseY));

        }
        if(IntersectionDetector2D.pointInBox2D( new Vector2(100,100), new Box2D( new Vector2(),new Vector2(50,50), transformComponent))){
           System.out.print("wa");
        }


    }
    @Override
    public void componentRender() {
        int a =(int) transformComponent.getPosition().y;
        int b =(int) transformComponent.getPosition().x;
        parent.getGameContainer().getRenderer().drawRectangle(a-25,b -25,50,50 );
    }
}
