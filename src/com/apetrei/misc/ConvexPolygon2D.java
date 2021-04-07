package com.apetrei.misc;

import java.awt.geom.Point2D;

public class ConvexPolygon2D {

    private Vector2[] vertices;

    public ConvexPolygon2D( Vector2[] vertices ){
        this.vertices = vertices;
    }

    public ConvexPolygon2D( ConvexPolygon2D poligon ){
        this.vertices = poligon.vertices.clone();
    }


    //___________________GETTER__AND__SETTER

    public Vector2[] getVertices() {
        return vertices;
    }

    public void setVertices(Vector2[] vertices) {
        this.vertices = vertices;
    }


}
