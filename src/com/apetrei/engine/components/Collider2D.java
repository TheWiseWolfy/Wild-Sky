package com.apetrei.engine.components;

/*!
 * Interfata generica pentru toate tipurile de colider din joc:
 * Poligon - o forma de colider custom.
 * AABB - Un colider primitiv care incadreaza obiectul intr-un patrat aliniat la axe.
 */

public class Collider2D extends Component {

    protected Rigidbody2D rigidbody = null;

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
