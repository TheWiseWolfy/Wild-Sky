package com.apetrei.engine.components;

import com.apetrei.misc.Vector2;
/*!
 * Componenta simpla care retine pozitia tuturor obiectelor din joc si rotatia lor
 */
public class TransformComponent extends Component {
    protected Vector2 position = new Vector2();
    protected float rotation = 0.0f;

    //Initializam obiectul la pozitia (0,0)
    public TransformComponent() {
        super();
    }

    @Override
    public void componentInit() {

    }

    //Initializam obiectul la o pozitie data.
    public TransformComponent(Vector2 pos) {
        super();
        position = pos;
    }

    @Override
    public void componentUpdate(double fT) {
    }

    @Override
    public void componentRender() {
    }

    //_________________________________GETTERS_AND_SETTERS__________________________

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
