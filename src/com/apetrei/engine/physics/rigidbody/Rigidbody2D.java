package com.apetrei.engine.physics.rigidbody;

import com.apetrei.engine.components.TransformComponent;
import com.apetrei.engine.components.Collider2D;
import com.apetrei.misc.Vector2;

public class Rigidbody2D extends TransformComponent {

    private float mass = 1.0f;
    private float inverseMass = 0.0f;

    private Vector2 forceAccum = new Vector2();
    private Vector2 linearVelocity = new Vector2();
    private float linearDamping = 0.0f;
    private float cor = 1.0f;       //Coeficient of restitution ??

    private Collider2D collider2D = null;

    public Rigidbody2D() {
        super();

        collider2D = (Collider2D) parent.getComponent( Collider2D.class);
    }

    public Rigidbody2D(Vector2 pos) {
        super(pos);


    }


    @Override
    public void componentInit() {
    }

    @Override
    public void componentUpdate(double fT) {

        if (this.mass == 0.0f) return;

        // Calculate linear velocity
        Vector2 acceleration = new Vector2(forceAccum).mul(this.inverseMass);
        linearVelocity.add(acceleration.mul((float)fT));

        // Update the linear position
        super.position.add(new Vector2(linearVelocity).mul((float)fT));

        clearAccumulators();
    }

    @Override
    public void componentRender() {

    }



    public void clearAccumulators() {
        this.forceAccum.set(0,0);
    }

    public void addForce(Vector2 force) {
        this.forceAccum.add(force);
    }

    public boolean hasInfiniteMass() {
        return this.mass == 0.0f;
    }

    //___________________________SETTERS_____________________
    public void setTransform(Vector2 position, float rotation) {
        super.position.set(position);
        super.rotation = rotation;
    }

    public void setTransform(Vector2 position) {
        super.position.set(position);
    }

    public void setMass(float mass) {
        this.mass = mass;
        if (this.mass != 0.0f) {
            this.inverseMass = 1.0f / this.mass;
        }
    }

    public void setLinearVelocity(Vector2 vel){
        linearVelocity.set(vel);
    }

    public void setCor(float cor) {
        this.cor = cor;
    }
    //________________________________GETTERS_________________

    public float getCor() {
        return cor;
    }


    public float getRotation() {
        return super.rotation;
    }

    public float getMass() {
        return mass;
    }

    public float getInverseMass() {
        return inverseMass;
    }

    public Vector2 getLinearVelocity(){
        return linearVelocity;
    }

    public Collider2D getCollider2D() {
        return collider2D;
    }

}
