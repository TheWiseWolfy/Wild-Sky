package com.apetrei.engine.physics.rigidbody;

import com.apetrei.misc.Vector2;

import java.util.ArrayList;
import java.util.List;

public class CollisionManifold {

    private final boolean isColliding;
    private final Vector2 normal;
    private final float depth;
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