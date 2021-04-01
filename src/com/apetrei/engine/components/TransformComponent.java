package com.apetrei.engine.components;

import com.apetrei.engine.components.Component;
import com.apetrei.engine.GameObject;
import com.apetrei.misc.Vector2;

public class TransformComponent extends Component {
    private Vector2 position = new Vector2();
    private float rotation = 0.0f;

    public TransformComponent(GameObject _parent) {
        super(_parent);
    }

    public TransformComponent(GameObject _parent, Vector2 pos) {
        super(_parent);
        position = pos;
    }

    public TransformComponent(Vector2 pos) {
        super();
    }


    @Override
    public void componentUpdate(double fT) {

    }

    @Override
    public void componentRender() {

    }

    //__________________FLUF____________________

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
