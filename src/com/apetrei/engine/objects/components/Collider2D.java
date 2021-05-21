package com.apetrei.engine.objects.components;

/*!
 * Interfata generica pentru toate tipurile de colider din joc:
 * Poligon - o forma de colider custom.
 * AABB - Un colider primitiv care incadreaza obiectul intr-un patrat aliniat la axe.
 */

import com.apetrei.misc.command.ColliderCommand;

public abstract class Collider2D extends Component {

    protected Rigidbody2D rigidbody = null;

    private boolean isTrigger;
    private ColliderCommand command;

    public Collider2D(boolean isTrigger) {
        super();
        this.isTrigger = isTrigger;
        this.command = null;
    }

    public Collider2D(boolean isTrigger, ColliderCommand command) {
        super();
        this.isTrigger = isTrigger;
        this.command = command;
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
    abstract public void componentUpdate(double fT);
    @Override
    abstract public void componentRender();

    public void onCollision(Collider2D collided){
        if(command != null) {
            command.execute(collided);
        }
    }

    public boolean isColliderTrigger(){
        return isTrigger;
    }

    //______________________GETTER_AND_SETTER_______________________

    public Rigidbody2D getRigidbody() {
        return rigidbody;
    }

    public void setRigidbody(Rigidbody2D rigidbody) {
        this.rigidbody = rigidbody;
    }
}
