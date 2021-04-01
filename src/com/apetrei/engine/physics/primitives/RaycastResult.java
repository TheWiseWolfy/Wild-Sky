package com.apetrei.engine.physics.primitives;

import com.apetrei.misc.Vector2;

public class RaycastResult {
    private Vector2 point;    //Point at witch the raycast hit an object
    private Vector2 normal;    //Normala suprafetei in puctul lovit
    private float t;
    private boolean hit;

    public RaycastResult(){
        this.normal = new Vector2();
        this.normal = new Vector2();
        this.t = -1;
        this.hit = false;
    }

    public void init (Vector2 point, Vector2 normal, float t, boolean hit){
        this.point.set(point);
        this.normal.set(normal);
        this.hit = hit;
        this.t = t;
    }

    public static void reset(RaycastResult result) {
        if (result != null) {

            result.point.x = 0;
            result.point.y = 0;

            result.normal.x = 0;
            result.normal.y = 0;

            result.t = -1;
            result.hit = false;
        }
    }
}
