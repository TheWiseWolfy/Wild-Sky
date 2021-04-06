package com.apetrei.engine.components;

import com.apetrei.engine.components.Component;
import com.apetrei.engine.GameObject;
import com.apetrei.misc.Vector2;

public class Collider2D extends Component {
    protected Vector2 offset = new Vector2();
    protected TransformComponent transform = null;

    //TODO: IMPLEMENT THIS
    //public abstract  double getInertiaTensor(double mass);

    public Collider2D() {
        super();
    }

    @Override
    public void componentInit() {
        try {
            transform = (TransformComponent) parent.getComponent(TransformComponent.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void componentUpdate(double fT) {

    }

    @Override
    public void componentRender() {

    }
}
