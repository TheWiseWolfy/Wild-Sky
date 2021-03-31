package com.apetrei.engine.physics.rigidbody;

import com.apetrei.engine.physics.primitives.AABB;
import com.apetrei.engine.physics.primitives.Box2D;
import com.apetrei.misc.Line;
import com.apetrei.misc.ExtraMath;
import com.apetrei.misc.Vector2;

public class IntersectionDetector2D {

    //POINT DETECTION

    public  static boolean pointOnLine(Vector2 point, Line line){
        float dy = line.getEnd().y - line.getStart().y;
        float dx = line.getEnd().x - line.getStart().x;

        if(dx == 0f){
            return  ExtraMath.compare( point.x , line.getStart().x);
        }

        float m = dy / dx;

        float b = line.getEnd().y - (m * line.getEnd().x);

        // Check the line equation
        return point.y == m * point.x + b;
    }

    //Fuctia asta detecteaza daca puctul dat se afla intr-un Axil Aligned Bounding Box
    public static boolean pointInAABB(Vector2 point, AABB box) {
        Vector2 min = box.getMin();
        Vector2 max = box.getMax();

        return point.x <= max.x && min.x <= point.x &&
                point.y <= max.y && min.y <= point.y;
    }

    //Fuctia asta detecteaza daca puctul dat e intr-o cutie
    public static boolean pointInBox2D(Vector2 point, Box2D box) {

        //Cream o axa locala ca sa fie totul usor
        Vector2 localSpacePoint = new Vector2(point);
        ExtraMath.rotateVec(localSpacePoint, box.getTransform().getRotation(), box.getTransform().getPosition());

        //Gasim maximul si minimul cutiei in gamespace
        Vector2 min = box.getMin();
        Vector2 max = box.getMax();

        return localSpacePoint.x <= max.x && min.x <= localSpacePoint.x &&
                localSpacePoint.y <= max.y && min.y <= localSpacePoint.y;
    }

    //LINE DETECTION

    public static  boolean lineInAABB(Line line, AABB box){
        //Daca unul din capetele segmentului e in cutie, coliziunea e garantata
        if(pointInAABB(line.getStart(),box) || pointInAABB( line.getEnd(), box)){
            return true;
        };
        //Daca toate puctele cutiei sunt pe aceasi parte a segmentului, nu are cum sa existe contact

        //Vectorul cu startul in origine
        Vector2 unitVector = new Vector2(line.getEnd()).sub(line.getStart());
        unitVector.normalize();

        unitVector.x = (unitVector.x != 0) ? 1.0f / unitVector.x : 0f;
        unitVector.y = (unitVector.y != 0) ? 1.0f / unitVector.y : 0f;
        //TODO this is hard

        Vector2 min = box.getMin();
        min.sub(line.getStart().mul(unitVector));
       // Vector2 max = box.getMax();
      //  max.sub(line.getStart()).max(unitVector);

    //    float tmin = Math.max(Math.min(min.x, max.x), Math.min(min.y,max.y));
        return false;
    }

}
