package com.apetrei.engine.components;

import com.apetrei.engine.*;

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
        if(parent.getGameContainer().getInput().isMouseKeyPressed(1)){
            transformComponent.y = parent.getGameContainer().getInput().getMouseY() * ConfigHandler.getScale() ;
            transformComponent.x = parent.getGameContainer().getInput().getMouseX()* ConfigHandler.getScale();
        }

    }
    @Override
    public void componentRender() {

    }
}
