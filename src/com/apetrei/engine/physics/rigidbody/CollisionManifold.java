package com.apetrei.engine.physics.rigidbody;

import com.apetrei.misc.Vector2;
import java.util.List;

/*!
 * Incapsulare date pentru verificarea coliziuni intre 2 obiecte
 */
public class CollisionManifold {

    //Verificam daca coliziunea este una valida
    private final boolean isColliding;

    //Normala coliziuni
    private final Vector2 normal;

    //Adancimea intrepatruderi curente a obiectelor
    private final float depth;

    //Puctele de intersectie dintre obiecte, si putele unui obiect care se afla si in celalant.
    private List<Vector2> contactPoints;

    public CollisionManifold() {
        normal = new Vector2();
        depth = 0.0f;
        isColliding = false;
    }

    public CollisionManifold(Vector2 normal, float depth,List<Vector2> contactPoints ) {
        this.normal = normal;
        this.depth = depth;
        this.contactPoints = contactPoints;
        isColliding = true;
    }

    public void addContactPoint(Vector2 contact) {
        this.contactPoints.add(contact);
    }

    public boolean isColliding(){
        return isColliding;
    }

    //____________________________________________GETTERS_________________________________
    public Vector2 getNormal() {
        return normal;
    }

    public List<Vector2> getContactPoints() {
        return contactPoints;
    }

    public float getDepth() {
        return depth;
    }

}