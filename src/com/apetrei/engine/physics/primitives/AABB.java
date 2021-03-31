package com.apetrei.engine.physics.primitives;

import com.apetrei.engine.components.TransformComponent;
import com.apetrei.misc.Vector2;

//Axis Aligned Bouding Box
public class AABB {
    private Vector2 center = new Vector2();
    private Vector2 halfSize = new Vector2();
    private Vector2 size = new Vector2();

    private TransformComponent transform = null;

    public AABB(Vector2 min, Vector2 max){
        this.size = new Vector2(max).sub(min);

        this.halfSize = new Vector2(size).mul(0.5f);
    }

    public Vector2 getMin(){
        return new Vector2(this.transform.getPosition()).sub(this.halfSize);
    }

    public Vector2 getMax(){
        return new Vector2(this.transform.getPosition().add(this.halfSize));
    }
}
