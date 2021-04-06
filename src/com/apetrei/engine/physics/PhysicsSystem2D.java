package com.apetrei.engine.physics;

import com.apetrei.engine.physics.forces.ForceRegistry;
import com.apetrei.engine.components.Collider2D;
import com.apetrei.engine.physics.rigidbody.CollisionManifold;
import com.apetrei.engine.physics.rigidbody.Collisions;
import com.apetrei.engine.physics.rigidbody.Rigidbody2D;
import com.apetrei.misc.Vector2;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSystem2D {
    private ForceRegistry forceRegistry;
    private float fixedUpdate;
    private int impulseIterations = 6;
    //Force generators:
    //private Wind wind;

    private List<Rigidbody2D> rigidbodies;
    private List<Rigidbody2D> bodies1;
    private List<Rigidbody2D> bodies2;
    private List<CollisionManifold> collisions;


    public PhysicsSystem2D(float fixedUpdateDt){
        forceRegistry = new ForceRegistry();
        rigidbodies = new ArrayList<>();
        fixedUpdate = fixedUpdateDt;
    }

    public void update(float dt){

    }

    public void fixedUpdate(){
        bodies1.clear();
        bodies2.clear();
        collisions.clear();

        //Find any collisions
        int size = rigidbodies.size();
        //This would work way better as a quad tree
        for(int i = 0; i < size; i++){
            for (int j =i; j < i; j ++){
                if(i == j) continue;

                CollisionManifold result = new CollisionManifold();

                Rigidbody2D r1 = rigidbodies.get(i);
                Rigidbody2D r2 = rigidbodies.get(j);

                Collider2D c1 = r1.getCollider2D();
                Collider2D c2 = r2.getCollider2D();

                if( c1 != null && c2 != null && r1.hasInfiniteMass() && r2.hasInfiniteMass()){
                    result = Collisions.findCollisionFeatures(c1,c2);
                }

                if(result != null && result.isColliding()){
                    bodies1.add(r1);
                    bodies2.add(r2);

                    collisions.add(result);
                }
            }
        }
        //Update forces
        forceRegistry.updateForces(fixedUpdate);

        //Resolve the colisions vis iterative impuse resolution
        //iterate a certaim amount of times to get an approximate solution

        for(int k =0; k < impulseIterations; k++){
            for (int i = 0; i < collisions.size(); i++){
               int jSize = collisions.get(i).getContactPoints().size();
               for( int j = 0; j < jSize; j ++){
                   Rigidbody2D r1 = bodies1.get(i);
                   Rigidbody2D r2 = bodies2.get(i);
                   applyImpluse(r1,r2,collisions.get(i));
               }
            }
        }
        //Update the velocities of all ridigbodies
    }

    private void applyImpluse(Rigidbody2D a, Rigidbody2D b, CollisionManifold m){
        //Linear velocity

        float invMass1 = a.getInverseMass();
        float invMass2 = b.getInverseMass();
        float invMassSum = invMass1 + invMass2;

        assert (invMassSum > 0);

        //Relative velocity

        Vector2 relativeVel = new Vector2(b.getLinearVelocity()).sub(b.getLinearVelocity());
        Vector2 relativeNormal = new Vector2(m.getNormal());
        relativeNormal.normalize();

        if(relativeVel.dot(relativeNormal) > 0.0f){   //Daca obiectele se departeaza (aka produsul vectorial e mai mare ca 0) atunci nu facem nimic
            return;
        }

        float e = Math.min(a.getCor(),b.getCor());
        float numerator = (-(1.0f + e) * relativeVel.dot(relativeNormal));
        float j = numerator / invMassSum;

        if (m.getContactPoints().size() > 0 && j != 0.0f) {
            j /= (float)m.getContactPoints().size();    //Distribuim impulsul calculat asupra tuturor puctelor de contact pe care le-am inregistrat
        }

        Vector2 impulse = new Vector2(relativeNormal).mul(j);
        a.setLinearVelocity( new Vector2( a.getLinearVelocity() ).add( new Vector2( impulse ).mul(invMass1).mul( -1f ) ) );
        b.setLinearVelocity( new Vector2( b.getLinearVelocity() ).add( new Vector2( impulse ).mul(invMass2).mul( 1f ) ) );
    }


    public void addRigidbodies(Rigidbody2D body){
        this.rigidbodies.add(body);

    }
}


