package com.apetrei.misc;

import com.apetrei.misc.Vector2;

public class Line {
    private Vector2 from;
    private Vector2 to;

    public Line(Vector2 _from, Vector2 _to) {
        this.from = _from;
        this.to = _to;
    }

    public Vector2 getStart() {
        return this.from;
    }

    public Vector2 getEnd() {
        return this.to;
    }

    public float lenghtSquared(){
        return new Vector2(to).sub(from).lenghtSquared();
    }

}