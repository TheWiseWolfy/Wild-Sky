package com.apetrei.misc;

import com.apetrei.misc.Vector2;

public class Line {
    private Vector2 A;
    private Vector2 B;

    public Line() {
        this.A = new Vector2();
        this.B = new Vector2();
    }

    public Line(Vector2 A, Vector2 B) {
        this.A = A;
        this.B = B;
    }

    public Vector2 findNormal(){
        Vector2 normal = new Vector2();

        float dx = B.x - A.x;
        float dy = B.y - A.y;

        normal.x = dy;
        normal.y = -dx;
        normal.normalize();
        return normal;
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

    @Override
    public String toString() {
        return "Line{" +
                "A=" + A +
                ", B=" + B +
                '}';
    }
}