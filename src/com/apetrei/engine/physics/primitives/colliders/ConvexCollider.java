package com.apetrei.engine.physics.primitives.colliders;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.engine.objects.components.Collider2D;
import com.apetrei.engine.renderer.Renderer;
import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.misc.Vector2;
import com.apetrei.misc.command.ColliderCommand;

import java.util.ArrayList;
import java.util.List;

/*!
 * Casa care gestioneaza un colider complex cu multiple pucte
 */
public class ConvexCollider extends Collider2D {

    final private ConvexPolygon2D collider;

    public ConvexCollider(boolean isTrigger, ConvexPolygon2D poligon,  ColliderCommand command){
        super(isTrigger, command);
        this.collider = new ConvexPolygon2D(poligon);
    }


    public ConvexCollider(boolean isTrigger, ConvexPolygon2D poligon){
        super(isTrigger);
        this.collider = new ConvexPolygon2D(poligon);
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

    public void showColider(Renderer renderer){
        ConvexPolygon2D local = getLocalPoligon();
        renderer.getLayerRenderer().drawPoligon(local);
    }

    @Override
    public void componentUpdate(double fT) {

    }

    public void componentRender() {
        if(ConfigHandler.isDebugMode() ) {
            showColider(this.getParent().getGameContainer().getRenderer());
        }
    }


}
