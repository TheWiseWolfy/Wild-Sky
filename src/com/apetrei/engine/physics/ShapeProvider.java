package com.apetrei.engine.physics;

import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.misc.Vector2;

import java.util.ArrayList;
import java.util.List;

public class ShapeProvider {
    public static  ConvexPolygon2D  getFinishLineCollider(){

        List<Vector2> shape = new ArrayList<Vector2>();

        shape.add(new Vector2(-200, 1300));
        shape.add(new Vector2(200, 1300));
        shape.add(new Vector2(200, -1300));
        shape.add(new Vector2(-200, -1300));

        return new ConvexPolygon2D(shape);
    }

    public static  ConvexPolygon2D getProjectileCollider(){

        List<Vector2> shape = new ArrayList<Vector2>();

        shape.add(new Vector2(-10, 10));
        shape.add(new Vector2(10, 10));
        shape.add(new Vector2(10, -10));
        shape.add(new Vector2(-10, -10));

        return new ConvexPolygon2D(shape);
    }

    public static ConvexPolygon2D getZepelinCollider(){

        List<Vector2>  shape = new ArrayList<Vector2>();

        shape.add(new Vector2(-20, 50));
        shape.add(new Vector2(30, 50));
        shape.add(new Vector2(70, 0));
        shape.add(new Vector2(30, -50));
        shape.add(new Vector2(-30, -50));
        shape.add(new Vector2(-70, 0));


        return new ConvexPolygon2D(shape);
    }

    public static ConvexPolygon2D getDockCollider(){
        List<Vector2>  shape = new ArrayList<Vector2>();

        shape.add(new Vector2(-520, 150));
        shape.add(new Vector2(440, 150));
        shape.add(new Vector2(440, -200));
        shape.add(new Vector2(-520, -200));

        return new ConvexPolygon2D(shape);
    }

    public static ConvexPolygon2D getLighthouseCollider(){
        List<Vector2>  shape = new ArrayList<Vector2>();

        float a =22;
        shape.add(new Vector2(-10 * a,5 * a ));
        shape.add(new Vector2(-5 * a,10 * a));
        shape.add(new Vector2(5 * a,10 * a ));
        shape.add(new Vector2(10 * a,5 * a));
        shape.add(new Vector2(10 * a,-5 * a));
        shape.add(new Vector2(5 * a,-10 * a ));
        shape.add(new Vector2(-5 * a,-10 * a));
        shape.add(new Vector2(-10 * a,-5* a));

        return new ConvexPolygon2D(shape);
    }

    public static ConvexPolygon2D getBaloonCollider(){
        List<Vector2>  shape = new ArrayList<Vector2>();

        shape.add(new Vector2(-40, 30));
        shape.add(new Vector2(50, 30));
        shape.add(new Vector2(50, -60));
        shape.add(new Vector2(-40, -60));

        return new ConvexPolygon2D(shape);
    }


}
