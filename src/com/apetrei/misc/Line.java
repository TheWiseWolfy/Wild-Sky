package com.apetrei.misc;

import com.apetrei.misc.Vector2;

public class Line {
    private Vector2 from;
    private Vector2 to;

    public Line(Vector2 from, Vector2 to) {
        this.from = from;
        this.to = to;
    }


    public Vector2 getFrom() {
        return from;
    }

    public Vector2 getTo() {
        return to;
    }

    public Vector2 getStart() {
        return this.from;
    }

    public Vector2 getEnd() {
        return this.to;
    }

}