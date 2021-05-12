package com.apetrei.engine.physics;

import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.misc.Vector2;

import java.util.ArrayList;
import java.util.List;

public class ShapeProvider {
    public static  ConvexPolygon2D  getProjectileColider(){

        List<Vector2> shape = new ArrayList<Vector2>();

        shape.add(new Vector2(-10, 10));
        shape.add(new Vector2(10, 10));
        shape.add(new Vector2(10, -10));
        shape.add(new Vector2(-10, -10));

        return new ConvexPolygon2D(shape);
    }

    public static ConvexPolygon2D getZepelinColider(){

        List<Vector2>  shape = new ArrayList<Vector2>();

        shape.add(new Vector2(-20, 50));
        shape.add(new Vector2(30, 50));
        shape.add(new Vector2(70, 0));
        shape.add(new Vector2(30, -50));
        shape.add(new Vector2(-30, -50));
        shape.add(new Vector2(-70, 0));


        return new ConvexPolygon2D(shape);
    }

    public static ConvexPolygon2D getDockColider(){
        List<Vector2>  shape = new ArrayList<Vector2>();

        shape.add(new Vector2(-520, 150));
        shape.add(new Vector2(440, 150));
        shape.add(new Vector2(440, -200));
        shape.add(new Vector2(-520, -200));

        return new ConvexPolygon2D(shape);
    }
}
