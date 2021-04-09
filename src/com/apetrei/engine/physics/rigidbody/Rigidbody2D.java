package com.apetrei.engine.physics.rigidbody;

import com.apetrei.engine.components.TransformComponent;
import com.apetrei.engine.components.Collider2D;
import com.apetrei.misc.Vector2;

import static java.lang.Math.*;

public class Rigidbody2D extends TransformComponent {

    private float mass = 1.0f;
    private float inverseMass = 0.0f;

    private Vector2 forceAccumulation = new Vector2();

    private Vector2 linearVelocity = new Vector2();
    private float linearDamping = 0.0f;
    private float cor = 1f;       //Coeficient of restitution ??

    private Vector2 forward = new Vector2();

    public Rigidbody2D() {
        super();
    }

    public Rigidbody2D(Vector2 pos,float mass) {
        super(pos);
        this.mass = mass;
    }


    @Override
    public void componentInit() {

    }

    @Override
    public void componentUpdate(double fT) {

        if (this.mass == 0.0f) return;

        // Calculate linear velocity
        Vector2 acceleration = new Vector2(forceAccumulation).mul(this.inverseMass);

        linearVelocity = linearVelocity.add(acceleration.mul((float)fT));

        // Update the linear position
        super.position = super.position.add(new Vector2(linearVelocity).mul((float)fT));

        clearAccumulators();
    }

    @Override
    public void componentRender() {

    }
    public void clearAccumulators() {
        this.forceAccumulation.set(0,0);
    }

    public void addForce(Vector2 force) {
        this.forceAccumulation.add(force);
    }

    public boolean hasInfiniteMass() {
        return this.mass == 0.0f;
    }

    //___________________________SETTERS_____________________
    public void setTransform(Vector2 position, float rotation) {
        super.position.set(position);
        super.rotation = rotation;
    }

    public void setLinearVelocity(Vector2 vel){
        linearVelocity.set(vel);
    }

    public void setCor(float cor) {
        this.cor = cor;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    //________________________________GETTERS___________________

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
        if (this.mass != 0.0f) {
            this.inverseMass = 1.0f / this.mass;
        }
        return inverseMass;
    }

    public Vector2 getLinearVelocity(){
        return linearVelocity;
    }

    public Vector2 getForward() {
        forward.x = (float)cos(rotation);
        forward.y = (float)sin(rotation);
        forward = forward.normalized();
        return forward;
    }   //Vectorul asta o sa fie indreptat intodeauna in fata jucatorului

}
