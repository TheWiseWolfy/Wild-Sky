package com.apetrei.engine.physics;

import com.apetrei.engine.objects.GameObject;
import com.apetrei.engine.objects.components.Collider2D;
import com.apetrei.engine.physics.rigidbody.CollisionManifold;
import com.apetrei.engine.physics.rigidbody.Collisions;
import com.apetrei.engine.objects.components.Rigidbody2D;
import com.apetrei.misc.Vector2;
import com.apetrei.misc.exceptions.ComponentMissingException;
import com.apetrei.misc.observer.ObjectManagerObserver;
import com.apetrei.engine.GameContainer;

import java.util.*;


/*!
 * Aceasta clasa mentine momentan 3 responsabilitati.
 *      - Gestioneaza ce obiecte o sa fie monitorizate in cazul verificarilor de coliziune
 *      - Monitorizeaza un set de obicte in scopul detectari unei coliziuni
 *      - Atunci cand o coliziune este detectata, in aceasta fuctie va fi calculata si transmisa consecinta aceste coliziuni.
 */
public class PhysicsSystem2D implements ObjectManagerObserver {
    GameContainer gameContainer;
    private int impulseIterations = 5;

    private List<Collider2D> colliders;
    private List<Rigidbody2D> bodies1;
    private List<Rigidbody2D> bodies2;
    private List<CollisionManifold> collisions;

    Set<AbstractMap.SimpleEntry<Integer, Integer>> activeCollisions = new HashSet<>();

    WindEffect windEffect;

    public PhysicsSystem2D(GameContainer gameContainer) {
        colliders = new ArrayList<>();
        bodies1 = new ArrayList<>();
        bodies2 = new ArrayList<>();
        collisions = new ArrayList<>();
        windEffect = new WindEffect(gameContainer);
    }

    public void updatePhysics(float fixedUpdate) {
        windEffect.update();
        updateColisions();
    }

    private void updateColisions( ){
        bodies1.clear();
        bodies2.clear();
        collisions.clear();

        //Find any collisions
        int size = colliders.size();


        //This would work way better as a quad tree
        for (int i = 0; i < size; i++) {
            for (int j = i; j < size; j++) {

                if (i == j) continue;

                CollisionManifold result = new CollisionManifold();

                Collider2D c1 = colliders.get(i);
                Collider2D c2 = colliders.get(j);

                Rigidbody2D r1 = c1.getRigidbody();
                Rigidbody2D r2 = c2.getRigidbody();

                if (c1 != null && c2 != null) {
                    result = Collisions.findCollisionFeatures(c1, c2);
                }
                if (result != null && result.isColliding()) {
                    if (!activeCollisions.contains( new AbstractMap.SimpleEntry<Integer,Integer>(i,j) )) {
                        activeCollisions.add(new AbstractMap.SimpleEntry<Integer,Integer>(i, j));
                        //c1 si c2 sunt cele 2 obiecte in coliziune
                        c1.onCollision(c2);
                        c2.onCollision(c1);
                    }
                    if ( !c1.isColliderTrigger() && !c2.isColliderTrigger() ) {
                        bodies1.add(r1);
                        bodies2.add(r2);
                        collisions.add(result);
                    }

                }else {
                    activeCollisions.remove( new AbstractMap.SimpleEntry<Integer,Integer>(i,j) );
                }
            }
        }

        // for(int k =0; k < impulseIterations; k++){
        for (int i = 0; i < collisions.size(); i++) {
            int jSize = collisions.get(i).getContactPoints().size();

            for (int j = 0; j < jSize; j++) {

                Rigidbody2D r1 = bodies1.get(i);
                Rigidbody2D r2 = bodies2.get(i);

                applyImpluse(r1, r2, collisions.get(i));
            }
        }
        // }
    }

    private void applyImpluse(Rigidbody2D a, Rigidbody2D b, CollisionManifold manifold) {
        //Linear velocity

        float invMass1 = a.getInverseMass() != -1 ? a.getInverseMass() : 0;
        float invMass2 = b.getInverseMass() != -1 ? b.getInverseMass() : 0;
        float invMassSum = invMass1 + invMass2;

        assert (invMassSum > 0);

        //Viteza relativa dintre 2 obiect
        Vector2 relativeVel = new Vector2(b.getLinearVelocity()).sub(a.getLinearVelocity());

        //Vectorul normal al coliziuni, intodeanua indreptat spre inafara obiectului
        Vector2 relativeNormal = new Vector2(manifold.getNormal());

        //Daca obiectele se departeaza (aka produsul vectorial e mai mare ca 0) atunci nu facem nimic
        if (relativeVel.dot(relativeNormal) > 0.0f) {
            return;
        }

        //Colisiunea va respecta principile obiectului mai elastic dintre cele 2 ( nu e realistic dar csf)
        float e = Math.min( a.getCor(), b.getCor() );

        float numerator = (-(1.0f + e) * relativeVel.dot(relativeNormal));
        float forceStrenght = numerator / invMassSum;

        if (manifold.getContactPoints().size() > 0 && forceStrenght != 0.0f) {
            //   forceStrenght /= (float)manifold.getContactPoints().size();    //Distribuim impulsul calculat asupra tuturor puctelor de contact pe care le-am inregistrat
        }
        forceStrenght *= manifold.getDepth();

        Vector2 impulse = new Vector2(relativeNormal).mul(forceStrenght);

        a.addForce(impulse.mul(-1.0f));
        b.addForce(impulse.mul(-1.0f));
    }

    private void addCollider(Collider2D collider) {
        this.colliders.add(collider);
    }

    private void removeCollider(Collider2D collider) {
        this.colliders.remove(collider);
    }

    public void resetPhysicsSystem() {
       // this.colliders.clear();
    }

    //_______________________________________GETTERS_______________________

    public WindEffect getWindEffect() {
        return windEffect;
    }

    //____________________________________OBJECT_OBSERVER_______________________

    @Override
    public void newObjectUpdate(GameObject created) {
        if( created.hasComponent(Collider2D.class)){
            try {
                addCollider( (Collider2D) created.getComponent( Collider2D.class));
            } catch (ComponentMissingException e) {
                System.err.println( "One with object with collisions has not been added to the Physics sistem.");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void objectDeletedUpdate(GameObject created) {
        if( created.hasComponent(Collider2D.class)){
            try {
                removeCollider( (Collider2D) created.getComponent( Collider2D.class));
            } catch (ComponentMissingException e) {
                System.err.println( "One object with collisions has not been coretly removed.");
                e.printStackTrace();
            }
        }
    }
}


