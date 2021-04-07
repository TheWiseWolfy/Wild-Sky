package com.apetrei.engine.physics.primitives.colliders;

import com.apetrei.engine.components.Collider2D;
import com.apetrei.engine.components.TransformComponent;
import com.apetrei.engine.physics.rigidbody.Rigidbody2D;
import com.apetrei.misc.Vector2;

//Axis Aligned Bouding Box
public class AABBCollider extends Collider2D {

    private Vector2 center = new Vector2();

    private Vector2 halfSize = new Vector2();
    private Vector2 size = new Vector2();

    public AABBCollider(Vector2 min, Vector2 max){
        if(rigidbody == null){
            rigidbody = new Rigidbody2D();
        }

        this.size = new Vector2(max).sub(min);
        this.halfSize = new Vector2(size).mul(0.5f);
    }

    public AABBCollider(Vector2 min, Vector2 max, Rigidbody2D rigidbody){
        this.rigidbody = rigidbody;
        this.size = new Vector2(max).sub(min);
        this.halfSize = new Vector2(size).mul(0.5f);
    }

    public Vector2 getMin(){
        return new Vector2(this.rigidbody.getPosition()).sub(this.halfSize);
    }

    public Vector2 getMax(){
        return new Vector2(this.rigidbody.getPosition()).add(this.halfSize);
    }
}
