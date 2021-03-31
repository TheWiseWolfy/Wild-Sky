package com.apetrei.engine.physics.primitives;

import com.apetrei.engine.components.Component;
import com.apetrei.engine.GameObject;
import com.apetrei.misc.Vector2;

public class Collider2D extends Component {
    protected Vector2 offset = new Vector2();

    //TODO: IMPLEMENT THIS
    //public abstract  double getInertiaTensor(double mass);


    public Collider2D(GameObject _parent) {
        super(_parent);
    }

    @Override
    public void componentUpdate(double fT) {

    }

    @Override
    public void componentRender() {

    }
}
