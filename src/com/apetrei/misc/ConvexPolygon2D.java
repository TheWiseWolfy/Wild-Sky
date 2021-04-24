package com.apetrei.misc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ConvexPolygon2D implements Serializable {

    private List<Vector2> vertices;

    public ConvexPolygon2D( ConvexPolygon2D con){
        this.vertices = new ArrayList<Vector2>(con.vertices);
    }

    public ConvexPolygon2D( List ver){
        this.vertices = new ArrayList<Vector2>(ver);
    }

    //___________________GETTER__AND__SETTER

    public List<Vector2> getVertices() {
        return vertices;
    }

}
