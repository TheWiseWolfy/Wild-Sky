package com.apetrei.engine.components;

import com.apetrei.engine.Component;
import com.apetrei.engine.GameContainer;
import com.apetrei.engine.GameObject;
import com.apetrei.engine.ObjectManager;

public class TransformComponent extends Component {
    double x;


    double y;
    double xV, yV;

    public TransformComponent(GameObject gameObject,int _x, int _y){
        super(gameObject);
        x =_x;
        y=_y;
    }

    @Override
    public void componentUpdate(double fT) {

    }

    @Override
    public void componentRender( ) {

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
