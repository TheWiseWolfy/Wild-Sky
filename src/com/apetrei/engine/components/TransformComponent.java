package com.apetrei.engine.components;

import com.apetrei.engine.Component;
import com.apetrei.engine.GameContainer;

public class TransformComponent  extends Component {
    double x;


    double y;
    double xV, yV;

    public TransformComponent(GameContainer gameContainer,int _x,int _y){
        super(gameContainer);
        x =_x;
        y=_y;
    }

    @Override
    public void componentUpdate(GameContainer gameContainer) {

    }

    @Override
    public void componentRender(GameContainer gameContainer) {

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
