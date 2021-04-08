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
    private int impulseIterations = 6;
    //Force generators:
    //private Wind wind;

    private List<Collider2D> colliders;
    private List<Rigidbody2D> bodies1;
    private List<Rigidbody2D> bodies2;
    private List<CollisionManifold> collisions;


    public PhysicsSystem2D(){
        forceRegistry = new ForceRegistry();
        colliders = new ArrayList<>();
        bodies1 = new ArrayList<>();
        bodies2 = new ArrayList<>();
        collisions = new ArrayList<>();
    }

    public void Update( float fixedUpdate){
        bodies1.clear();
        bodies2.clear();
        collisions.clear();

        //Find any collisions
        int size = colliders.size();

        //This would work way better as a quad tree
        for(int i = 0; i < size; i++){
            for (int j = i; j < size ; j ++){
                if(i == j) continue;

                CollisionManifold result = new CollisionManifold();

                Collider2D c1 = colliders.get(i);
                Collider2D c2 = colliders.get(j);

                Rigidbody2D r1 = c1.getRigidbody();
                Rigidbody2D r2 = c2.getRigidbody();

                if( c1 != null && c2 != null && !r1.hasInfiniteMass() && !r2.hasInfiniteMass()){
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
        //forceRegistry.updateForces(fixedUpdate);

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

        Vector2 relativeVel = new Vector2(b.getLinearVelocity()).sub(a.getLinearVelocity());
        Vector2 relativeNormal = new Vector2(m.getNormal());

        if(relativeVel.dot(relativeNormal) > 0.0f){   //Daca obiectele se departeaza (aka produsul vectorial e mai mare ca 0) atunci nu facem nimic
           return;
       }

       // float e = Math.min(a.getCor(),b.getCor());
        float numerator = (-1.0f  * relativeVel.dot(relativeNormal));
        float j = numerator / invMassSum;

        if (m.getContactPoints().size() > 0 && j != 0.0f) {
            j /= (float)m.getContactPoints().size();    //Distribuim impulsul calculat asupra tuturor puctelor de contact pe care le-am inregistrat
        }

        Vector2 impulse = new Vector2(relativeNormal).mul(j);

        Vector2 newVelA =  new Vector2( a.getLinearVelocity() ).add( new Vector2( impulse ).mul(invMass1).mul( -1f ) );
        Vector2 newVelB = new Vector2( b.getLinearVelocity() ).add( new Vector2( impulse ).mul(invMass2).mul( 1f ) );

        a.setLinearVelocity( newVelA );
        b.setLinearVelocity( newVelB );
    }

    //O fuctie prin care adaugem obiecte in sistemul de fizica TODO Make this automatic after a set criteria
    public void addRigidbodies(Collider2D body){
        this.colliders.add(body);
    }
}


