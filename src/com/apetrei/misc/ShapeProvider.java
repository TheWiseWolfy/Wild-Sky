package com.apetrei.misc;

import java.util.ArrayList;
import java.util.List;

public class ShapeProvider {
    public static List<Vector2> getProjectileColider(){

        List<Vector2> shape = new ArrayList<Vector2>();

        shape.add(new Vector2(-10, 10));
        shape.add(new Vector2(10, 10));
        shape.add(new Vector2(10, -10));
        shape.add(new Vector2(-10, -10));

        return shape;
    }
}
