package com.apetrei.engine.components;

import com.apetrei.engine.components.Component;
import com.apetrei.engine.GameObject;
import com.apetrei.engine.physics.rigidbody.Rigidbody2D;
import com.apetrei.misc.Vector2;

public class Collider2D extends Component {


    protected Rigidbody2D rigidbody = null;

    //TODO: IMPLEMENT THIS
    //public abstract  double getInertiaTensor(double mass);

    public Collider2D() {
        super();
    }

    @Override
    public void componentInit() {
        try {
            rigidbody = (Rigidbody2D) parent.getComponent(Rigidbody2D.class);
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

    //______________________GETTER_AND_SETTER_______________________

    public Rigidbody2D getRigidbody() {
        return rigidbody;
    }

    public void setRigidbody(Rigidbody2D rigidbody) {
        this.rigidbody = rigidbody;
    }
}
