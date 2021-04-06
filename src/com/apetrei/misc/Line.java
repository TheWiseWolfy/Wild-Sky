package com.apetrei.misc;

import com.apetrei.misc.Vector2;

public class Line {
    private Vector2 A;
    private Vector2 B;

    public Line(Vector2 A, Vector2 B) {
        this.A = A;
        this.B = B;
    }

    public Vector2 getA() {
        return this.A;
    }

    public Vector2 getB() {
        return this.B;
    }

    public float lenghtSquared(){
        return new Vector2(B).sub(A).lenghtSquared();
    }

}