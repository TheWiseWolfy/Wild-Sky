package com.apetrei.engine.physics.primitives.colliders;

import com.apetrei.engine.GameContainer;
import com.apetrei.engine.components.Collider2D;
import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.misc.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConvexCollider extends Collider2D {

    final private ConvexPolygon2D collider;

    public ConvexCollider( ConvexPolygon2D poligon){
        this.collider = new ConvexPolygon2D(poligon);
    }

    public ConvexPolygon2D getPoligon() {
        return collider;
    }

    public ConvexPolygon2D getLocalPoligon() {

        //Translation

        List<Vector2> points =  new ArrayList<>(collider.getVertices() );
        List<Vector2> localPoints = new ArrayList<>(points );

        //Rotation
        for (int i = 0; i < collider.getVertices().size() ; ++i) {

            Vector2 fuckYouJava = new Vector2( points.get(i));

            float rotatedX = (float) ( fuckYouJava.x * Math.cos(rigidbody.getRotation() ) - fuckYouJava.y * Math.sin(rigidbody.getRotation() ) ) ;
            float rotatedY = (float) ( fuckYouJava.x * Math.sin(rigidbody.getRotation() )  +  fuckYouJava.y * Math.cos(rigidbody.getRotation() ) ) ;

            localPoints.set(i, new Vector2(rotatedX ,rotatedY ));
        }

        for(int i = 0; i < collider.getVertices().size() ; ++i){
            localPoints.set(i , new Vector2(localPoints.get(i)) .add(rigidbody.getPosition()));
        }


        ConvexPolygon2D transformedCollider = new ConvexPolygon2D( localPoints  );

        return transformedCollider;
    }

    public void showColider(){
        ConvexPolygon2D local = getLocalPoligon();
        GameContainer.getInstance().getRenderer().drawPoligon(local);

    }

    public void componentRender() {
        showColider();
    }
}
