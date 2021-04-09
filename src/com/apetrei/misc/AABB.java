package com.apetrei.misc;

//Axis Aligned Bouding Box
public class AABB {

    private Vector2 position = new Vector2();

    private Vector2 halfSize = new Vector2();
    private Vector2 size = new Vector2();

    public AABB(Vector2 min, Vector2 max, Vector2 position){
        this.size = new Vector2(max).sub(min);
        this.halfSize = new Vector2(size).mul(0.5f);

    }

    public Vector2 getMin(){
        return  new Vector2( position ).sub( this.halfSize )  ;
    }

    public Vector2 getMax(){
        return  new Vector2( position ).add( this.halfSize )  ;
    }
}
