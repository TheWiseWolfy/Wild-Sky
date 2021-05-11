package com.apetrei.engine.objects.components;

import com.apetrei.misc.Vector2;

import static java.lang.Math.*;

/*!
 * Rigidbody este o extensie a "TransformComponent" care nu numai ca retine pozitia unui obiect, dar retine si viteza si aceleratia.
 * Desi asta nu era intentia initial, acum rigidbody gestioneaza in totalitate relatia intre forta - aceleratie - viteza - pozitie
 * Si este o incapsulare a sistemului de fizica din joc. Atunci cand vrei sa interactioneazi cu un obiect, cel mai recomandat ar fi
 * sa chemi "AddForce()" din Rigidbody-ul respectivului obiect.
 */
public class Rigidbody2D extends TransformComponent {

    private float mass = 1.0f;
    private float inverseMass = -1f;

    private Vector2 forceAccumulation = new Vector2();
    private Vector2 acceleration = new Vector2();
    private Vector2 linearVelocity = new Vector2();
    private float linearDamping = 0.2f;

    private float angularVelocity = 0f;
    private float angularDamping = 3f;
    private float angularAcceleration = 0;
    private float angularForceAcumulator = 0;

    private float cor = 1f;       //Coeficient of restitution ??

    private Vector2 forward = new Vector2();

    public Rigidbody2D() {
        super();
    }

    public Rigidbody2D(Vector2 pos, float mass) {
        super(pos);
        this.mass = mass;

        if (this.mass != 0.0f) {
            this.inverseMass = 1.0f / this.mass;
        }
    }

    public Rigidbody2D(Vector2 pos, Vector2 vel, float mass) {
        super(pos);
        this.linearVelocity = vel;
        this.mass = mass;

        if (this.mass != 0.0f) {
            this.inverseMass = 1.0f / this.mass;
        }
    }

    @Override
    public void componentInit() {
    }

    @Override
    public void componentUpdate(double fT) {

        if (this.mass == 0.0f) return;

        //ANGULAR BEHAVIOUR

        //Rezistenta Unghiulara cu Aerul
        angularVelocity = angularVelocity * (1 - angularDamping * (float) fT);

        //Rotational behaviour
        angularAcceleration = angularForceAcumulator * this.inverseMass;

        angularVelocity += angularAcceleration * (float) fT;

        rotation += angularVelocity * (float) fT;


        //LINEAR BEHAVIOUR

        //Aici aplicam "rezistenta cu aerul"
        linearVelocity = linearVelocity.mul(1 - linearDamping * (float) fT);

        //Calculate acceleration
        acceleration = new Vector2(forceAccumulation).mul(this.inverseMass);

        // Calculate linear velocity
        linearVelocity = linearVelocity.add(acceleration.mul((float) fT));

        // Update the linear position
        super.position = super.position.add(new Vector2(linearVelocity).mul((float) fT));

        //FINALLY
        clearAccumulators();
    }

    @Override
    public void componentRender() {
    }

    public void clearAccumulators() {
        this.forceAccumulation.set(0, 0);
        angularForceAcumulator = 0;

    }

    public void addForce(Vector2 force) {
        this.forceAccumulation.add(force);
    }

    public void addAngularForce(float force) {
        this.angularForceAcumulator += force;
    }

    public boolean isStaticObject() {
        return this.mass == 0.0f;
    }

    //___________________________SETTERS_____________________


    public void setCor(float cor) {
        this.cor = cor;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public void setAngularVelocity(float angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    public void setAngularDamping(float angularDamping) {
        this.angularDamping = angularDamping;
    }

    //________________________________GETTERS___________________


    public float getCor() {
        return cor;
    }

    public float getRotation() {
        return super.rotation;
    }

    public float getAngularDamping() {
        return angularDamping;
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

    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }

    public Vector2 getForward() {
        forward.x = (float) cos(rotation);
        forward.y = (float) sin(rotation);
        forward = forward.normalized();
        return forward;
    }   //Vectorul asta o sa fie indreptat intodeauna in fata jucatorului

    public float getAngularVelocity() {
        return angularVelocity;
    }

    public float getAngularAcceleration() {
        return angularAcceleration;
    }
}
