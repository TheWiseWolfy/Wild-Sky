package com.apetrei.engine.components;

import com.apetrei.engine.*;
import com.apetrei.engine.physics.primitives.Box2D;
import com.apetrei.engine.physics.primitives.IntersectionDetector2D;
import com.apetrei.misc.Line;
import com.apetrei.misc.Vector2;

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

        float mouseX =  parent.getGameContainer().getInput().getMouseX() * ConfigHandler.getScale() ;
        float mouseY = parent.getGameContainer().getInput().getMouseY()* ConfigHandler.getScale();

        if(parent.getGameContainer().getInput().isMouseKeyPressed(1)){
            transformComponent.setPosition(new Vector2(mouseX,mouseY));
        }
        transformComponent.setRotation(13);
    }
    @Override
    public void componentRender() {
        int a =(int) transformComponent.getPosition().x;
        int b =(int) transformComponent.getPosition().y;

        Line line = new Line( new Vector2(50,500), new Vector2(500,100));
        Box2D box = new Box2D(new Vector2(0,0), new Vector2( 120,120), transformComponent);

        //parent.getGameContainer().getRenderer().drawRectangle(box.getMin(), box.getMax() );
        parent.getGameContainer().getRenderer().drawBox(box);
        parent.getGameContainer().getRenderer().drawLine(line);

        if(IntersectionDetector2D.lineAndBox2D(line,box,parent)){
            System.out.print("Collision detected\n");
        }
    }
}
