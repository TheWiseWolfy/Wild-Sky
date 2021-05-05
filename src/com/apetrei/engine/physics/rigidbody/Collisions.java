package com.apetrei.engine.physics.rigidbody;

import com.apetrei.engine.objects.components.Collider2D;
import com.apetrei.engine.physics.primitives.colliders.ConvexCollider;
import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.misc.Line;
import com.apetrei.misc.Vector2;

import java.util.List;

/*!
 * Clasa statica care este menita sa poate gestiona toate tipurile de coliziune suportate de joc. Momentan exista un singur tip.
 * Informatia este transmisa dupa urmatoarea conventie: Collider2D IN, CollisionManifold OUT
 * TODO: ConvexPolygon2D to AABB and AABB to AABB collisions
 */
public class Collisions {

    public static CollisionManifold findCollisionFeatures(Collider2D c1, Collider2D c2) {
        //Cream un "Collision Manifold" care are
        CollisionManifold collisionManifold;
        Line colisionFront;

        ConvexPolygon2D poligon1 = ((ConvexCollider)c1).getLocalPoligon();
        ConvexPolygon2D poligon2 = ((ConvexCollider)c2).getLocalPoligon();

        List<Vector2> intersectie = IntersectionDetector2D.getPointsOfIntersectionFront(poligon1 ,poligon2) ;
        intersectie.addAll(  IntersectionDetector2D.getPointsInColision(poligon1,poligon2 ));

        if ( intersectie.size() >= 2){    //Am recuperat puctele de care aveam nevoie

           colisionFront = new Line( intersectie.get(0), intersectie.get(1) );
           Vector2 normal = colisionFront.findNormal();
           Vector2 relPoz = new Vector2(c1.getRigidbody().getPosition()).sub( c2.getRigidbody().getPosition());

           //Normala coliziune trebuie sa fie mereu intreptata inafara primului obiect din cele 2 prin conventia mea
            if (normal.dot(  relPoz  ) > 0f )
                normal.mul (-1);

            //TODO Calculate actual depth

            return new CollisionManifold(normal,10f, intersectie  );
        }else {
            return  new CollisionManifold();
        }

    }


}
