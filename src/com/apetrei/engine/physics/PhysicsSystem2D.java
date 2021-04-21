package com.apetrei.engine.physics;

import com.apetrei.engine.components.Collider2D;
import com.apetrei.engine.physics.rigidbody.CollisionManifold;
import com.apetrei.engine.physics.rigidbody.Collisions;
import com.apetrei.engine.physics.rigidbody.Rigidbody2D;
import com.apetrei.misc.Vector2;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSystem2D {
    private int impulseIterations = 5;
    //Force generators:
    //private Wind wind;

    private List<Collider2D> colliders;
    private List<Rigidbody2D> bodies1;
    private List<Rigidbody2D> bodies2;
    private List<CollisionManifold> collisions;


    public PhysicsSystem2D(){
        colliders = new ArrayList<>();
        bodies1 = new ArrayList<>();
        bodies2 = new ArrayList<>();
        collisions = new ArrayList<>();
    }

    public void updatePhysics( float fixedUpdate){
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



       // for(int k =0; k < impulseIterations; k++){
            for (int i = 0; i < collisions.size(); i++){
              int jSize = collisions.get(i).getContactPoints().size();

               for( int j = 0; j < jSize; j ++){
                   Rigidbody2D r1 = bodies1.get(i);
                   Rigidbody2D r2 = bodies2.get(i);
                   applyImpluse(r1,r2,collisions.get(i));
                }
           }
     // }
    }

    private void applyImpluse(Rigidbody2D a, Rigidbody2D b, CollisionManifold manifold){
        //Linear velocity

        float invMass1 = a.getInverseMass();
        float invMass2 = b.getInverseMass();
        float invMassSum = invMass1 + invMass2;


        assert (invMassSum > 0);

       //Viteza relativa dintre 2 obiect
        Vector2 relativeVel = new Vector2( b.getLinearVelocity()).sub(a.getLinearVelocity()) ;

        //Vectorul normal al coliziuni, intodeanua indreptat spre inafara obiectului
        Vector2 relativeNormal = new Vector2(manifold.getNormal());

        //Daca obiectele se departeaza (aka produsul vectorial e mai mare ca 0) atunci nu facem nimic
        if(relativeVel.dot(relativeNormal) > 0.0f){
           return;
       }

        //Colisiunea va respecta principile obiectului mai elastic dintre cele 2 ( nu e realistic dar csf)
        float e = Math.min(a.getCor(),b.getCor());

        float numerator = ( -(1.0f + e)   * relativeVel.dot(relativeNormal));
        float forceStrenght = numerator / invMassSum;

        if (manifold.getContactPoints().size() > 0 && forceStrenght != 0.0f) {
        //   forceStrenght /= (float)manifold.getContactPoints().size();    //Distribuim impulsul calculat asupra tuturor puctelor de contact pe care le-am inregistrat
        }
        forceStrenght *= manifold.getDepth();

        Vector2 impulse = new Vector2(relativeNormal).mul(forceStrenght);

        a.addForce( impulse.mul( -1.0f) );
        b.addForce( impulse.mul( -1.0f) );
    }

    //O fuctie prin care adaugem obiecte in sistemul de fizica TODO Make this automatic after a set criteria
    public void addColliders(Collider2D body){
        this.colliders.add(body);
    }
}


