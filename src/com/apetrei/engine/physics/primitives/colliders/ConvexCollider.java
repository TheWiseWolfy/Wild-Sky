package com.apetrei.engine.physics.primitives.colliders;

import com.apetrei.engine.components.Collider2D;
import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.misc.Vector2;

import java.util.ArrayList;
import java.util.List;

public class ConvexCollider extends Collider2D {

    private ConvexPolygon2D collider;
    private ConvexPolygon2D transformedCollider;

    public ConvexCollider( ConvexPolygon2D poligon){
        this.collider = poligon;
        transformedCollider = new ConvexPolygon2D(collider);
    }

    public ConvexPolygon2D getPoligon() {
        return collider;
    }

    public ConvexPolygon2D getLocalPoligon() {

        for(int i = 0; i < collider.getVertices().length ; ++i){

          Vector2 wac = new Vector2(( collider.getVertices() ) [i] );
            ( transformedCollider.getVertices() ) [i] =wac.add(rigidbody.getPosition());
        }

        return transformedCollider;
    }


    public void showColider(){
        ConvexPolygon2D local = getLocalPoligon();
        parent.getGameContainer().getRenderer().drawPoligon(local);

    }

    public void componentRender() {
        showColider();
    }
}
