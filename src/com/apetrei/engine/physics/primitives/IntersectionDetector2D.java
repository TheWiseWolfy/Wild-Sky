package com.apetrei.engine.physics.primitives;

import com.apetrei.engine.GameObject;
import com.apetrei.engine.physics.primitives.AABB;
import com.apetrei.engine.physics.primitives.Box2D;
import com.apetrei.misc.Line;
import com.apetrei.misc.ExtraMath;
import com.apetrei.misc.Vector2;

import java.awt.*;

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

        //Vectorul cu startul in origine si apoi normaliza
        Vector2 unitVector = new Vector2(line.getEnd()).sub(line.getStart());
        unitVector.normalize();

        //Asta e o masura de siguranta ?? TODO Write exception here
        unitVector.x = (unitVector.x != 0) ? 1.0f / unitVector.x : 0f;
        unitVector.y = (unitVector.y != 0) ? 1.0f / unitVector.y : 0f;

        //Acolo unde A - este vectorul de la startul liniei, la un colt al dreptunchiului
        // si u este vectorul unitate al liniei
        // Ax/ux rezulta intr-un scalar S
        //Scalarul S inmultit cu vectorul unitate de da vectorul relativ al puctului de coliziune

        Vector2 min = box.getMin();
        min.sub(line.getStart()).mul(unitVector);
        Vector2 max = box.getMax();
        max.sub(line.getStart()).mul(unitVector);

        //Obtinem 4 valori, din care trebuie sa alegem 2, celelante doua reprezinta coliziuni su dreptele
        //dreptunchiului, dar nu si cu segmentele sale.
        //Valorile reprezinta distanta catree prima si a doua coliziune
        float tmin = Math.max(Math.min(min.x, max.x), Math.min(min.y, max.y));
        float tmax = Math.min(Math.max(min.x, max.x), Math.max(min.y, max.y));

        //Puctul de intersectie mai indepartat trebuie sa fie
        // la distanta pozitiva de noi, si mai mare decat puctul de intersectie mai apropiat
        if (tmax < 0 || tmin > tmax) {
            return false;
        }

        //Ar fi ciudatt daca lungimea de la origine la tmin si de la tmin la tmax ar fi mai mare ca lungimea segmentului
        float t = (tmin < 0f) ? tmax : tmin;
        return t > 0f && t * t < line.lenghtSquared();

        //Nu-s matematician
        //https://www.youtube.com/watch?v=c065KoXooSw
    }

    public static boolean lineAndBox2D(Line line, Box2D box, GameObject parent) {

        float theta = -box.getTransform().getRotation();
        Vector2 center = box.getTransform().getPosition();

        Vector2 localStart = new Vector2(line.getStart());
        Vector2 localEnd = new Vector2(line.getEnd());

        ExtraMath.rotateVec(localStart, theta, center);
        ExtraMath.rotateVec(localEnd, theta, center);

        Line localLine = new Line(localStart, localEnd);

        AABB aabb = new AABB(box.getMin(), box.getMax(), box.getTransform());

      // parent.getGameContainer().getRenderer().drawLine(localLine, Color.RED);
      // parent.getGameContainer().getRenderer().drawRectangle(aabb.getMin(),aabb.getMax(),Color.RED);


        return lineInAABB(localLine, aabb);
    }//that is hard



}
