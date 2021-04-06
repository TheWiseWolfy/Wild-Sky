package com.apetrei.engine.physics.rigidbody;

import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.misc.Line;
import com.apetrei.misc.ExtraMath;
import com.apetrei.misc.Vector2;

import java.util.ArrayList;
import java.util.List;

public class IntersectionDetector2D {


    //POINT DETECTION
    public static boolean pointOnLine(Vector2 point, Line line) {
        float dy = line.getB().y - line.getA().y;
        float dx = line.getB().x - line.getA().x;

        if (dx == 0f) {
            return ExtraMath.compare(point.x, line.getA().x);
        }

        float m = dy / dx;

        float b = line.getB().y - (m * line.getB().x);
        float y_calculated = m * point.x + b;
        // Check the line equation
        return ExtraMath.compare(point.y, y_calculated , 0.001f);
    }
/*
    //Fuctia asta detecteaza daca puctul dat se afla intr-un Axil Aligned Bounding Box
    public static boolean pointInAABB(Vector2 point, AABBCollider box) {
        Vector2 min = box.getMin();
        Vector2 max = box.getMax();

        return point.x <= max.x && min.x <= point.x &&
                point.y <= max.y && min.y <= point.y;
    }

    //Fuctia asta detecteaza daca puctul dat e intr-o cutie
    public static boolean pointInBox2D(Vector2 point, Box2DCollider box) {

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

    public static  boolean lineInAABB(Line line, AABBCollider box){
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

    public static boolean lineAndBox2D(Line line, Box2DCollider box, GameObject parent) {

        float theta = -box.getTransform().getRotation();
        Vector2 center = box.getTransform().getPosition();

        Vector2 localStart = new Vector2(line.getStart());
        Vector2 localEnd = new Vector2(line.getEnd());

        ExtraMath.rotateVec(localStart, theta, center);
        ExtraMath.rotateVec(localEnd, theta, center);

        Line localLine = new Line(localStart, localEnd);

        AABBCollider aabbCollider = new AABBCollider(box.getMin(), box.getMax(), new TransformComponent());

        return lineInAABB(localLine, aabbCollider);
    }


    //RAYCAST

    //TODO Test this
    public static boolean raycast(AABBCollider box, Ray2D ray, RaycastResult result) {
        RaycastResult.reset(result);

        Vector2 unitVector = ray.getDirection();
        unitVector.normalize();
        unitVector.x = (unitVector.x != 0) ? 1.0f / unitVector.x : 0f;
        unitVector.y = (unitVector.y != 0) ? 1.0f / unitVector.y : 0f;

        Vector2 min = box.getMin();
        min.sub(ray.getOrigin()).mul(unitVector);
        Vector2 max = box.getMax();
        max.sub(ray.getOrigin()).mul(unitVector);

        float tmin = Math.max(Math.min(min.x, max.x), Math.min(min.y, max.y));
        float tmax = Math.min(Math.max(min.x, max.x), Math.max(min.y, max.y));

        if (tmax < 0 || tmin > tmax) {
            return false;
        }

        float t = (tmin < 0f) ? tmax : tmin;
        boolean hit = t > 0f; //&& t * t < ray.getMaximum();

        if (!hit) {
            return false;
        }

        if (result != null) {
            Vector2 point = new Vector2(ray.getOrigin()).add( new Vector2(ray.getDirection()).mul(t)  );
            Vector2 normal = new Vector2(ray.getOrigin()).sub(point);
            normal.normalize();

            result.init(point, normal, t, true);
        }
        return true;
    }

    //TODO Test this
    public static boolean raycast(Box2DCollider box, Ray2D ray, RaycastResult result) {
        RaycastResult.reset(result);

        Vector2 size = box.getHalfSize();
        Vector2 xAxis = new Vector2(1, 0);
        Vector2 yAxis = new Vector2(0, 1);

        ExtraMath.rotateVec(xAxis, -box.getTransform().getRotation(), new Vector2(0, 0));
        ExtraMath.rotateVec(yAxis, -box.getTransform().getRotation(), new Vector2(0, 0));

        Vector2 p = new Vector2(box.getTransform().getPosition()).sub(ray.getOrigin());
        // Project the direction of the ray onto each axis of the box
        Vector2 f = new Vector2(
                xAxis.dot(ray.getDirection()),
                yAxis.dot(ray.getDirection())
        );
        // Next, project p onto every axis of the box
        Vector2 e = new Vector2(
                xAxis.dot(p),
                yAxis.dot(p)
        );

        float[] tArr = {0, 0, 0, 0};
        for (int i=0; i < 2; i++) {
            if (ExtraMath.compare(f.get(i), 0)) {
                // If the ray is parallel to the current axis, and the origin of the
                // ray is not inside, we have no hit
                if (-e.get(i) - size.get(i) > 0 || -e.get(i) + size.get(i) < 0) {
                    return false;
                }
                f.setComponent(i, 0.00001f); // Set it to small value, to avoid divide by zero
            }
            tArr[i * 2 + 0] = (e.get(i) + size.get(i)) / f.get(i); // tmax for this axis
            tArr[i * 2 + 1] = (e.get(i) - size.get(i)) / f.get(i); // tmin for this axis
        }

        float tmin = Math.max(Math.min(tArr[0], tArr[1]), Math.min(tArr[2], tArr[3]));
        float tmax = Math.min(Math.max(tArr[0], tArr[1]), Math.max(tArr[2], tArr[3]));

        float t = (tmin < 0f) ? tmax : tmin;
        boolean hit = t > 0f; //&& t * t < ray.getMaximum();
        if (!hit) {
            return false;
        }

        if (result != null) {
            Vector2 point = new Vector2(ray.getOrigin()).add(
                    new Vector2(ray.getDirection()).mul(t));
            Vector2 normal = new Vector2(ray.getOrigin()).sub(point);
            normal.normalize();

            result.init(point, normal, t, true);
        }

        return true;
    }
  */

    static public Vector2 GetIntersectionPoint(Line line1, Line line2) {

        float A1 = line1.getB().y - line1.getA().y;
        float B1 = line1.getA().x - line1.getB().x;
        float C1 = A1 * line1.getA().x + B1 * line1.getA().y;

        float A2 = line2.getB().y - line2.getA().y;
        float B2 = line2.getA().x - line2.getB().x;
        float C2 = A2 * line2.getA().x + B2 * line2.getA().y;

        //lines are parallel
        float det = A1 * B2 - A2 * B1;
        if (ExtraMath.compare(det, 0f)) {
            return null; //parallel lines
        } else {
            float x = (B2 * C1 - B1 * C2) / det;
            float y = (A1 * C2 - A2 * C1) / det;
            Vector2 point = new Vector2(x, y);

            Vector2 l1p1 = line1.getA();
            Vector2 l1p2 = line1.getB();
            Vector2 l2p1 = line2.getA();
            Vector2 l2p2 = line2.getB();

            boolean online1 = ((Math.min(l1p1.x, l1p2.x) < x || ExtraMath.compare(Math.min(l1p1.x, l1p2.x), x))
                    && (Math.max(l1p1.x, l1p2.x) > x ||  ExtraMath.compare(Math.max(l1p1.x, l1p2.x), x))
                    && (Math.min(l1p1.y, l1p2.y) < y ||  ExtraMath.compare(Math.min(l1p1.y, l1p2.y), y))
                    && (Math.max(l1p1.y, l1p2.y) > y ||  ExtraMath.compare(Math.max(l1p1.y, l1p2.y), y))
            );
            boolean online2 = ((Math.min(l2p1.x, l2p2.x) < x ||  ExtraMath.compare(Math.min(l2p1.x, l2p2.x), x))
                    && (Math.max(l2p1.x, l2p2.x) > x ||  ExtraMath.compare(Math.max(l2p1.x, l2p2.x), x))
                    && (Math.min(l2p1.y, l2p2.y) < y ||  ExtraMath.compare(Math.min(l2p1.x, l2p2.x), y))
                    && (Math.max(l2p1.y, l2p2.y) > y ||  ExtraMath.compare(Math.max(l2p1.x, l2p2.x), y))
            );

            if (online1 && online2)
                return point;
        }
        return null; //intersection is at out of at least one segment.
    }

   static  public List<Vector2> GetIntersectionPoints(Line line1, ConvexPolygon2D poly) {
        List<Vector2> intersectionPoints = new ArrayList<Vector2>();
        for (int i = 0; i < poly.vertices.length; i++) {
            int next = (i + 1 == poly.vertices.length) ? 0 : i + 1;  //TODO Poate module e mai rapid ca asta ??

            Vector2 ip = GetIntersectionPoint(line1, new Line(poly.vertices[i], poly.vertices[next]));

            if (ip != null) intersectionPoints.add(ip);
        }

        return intersectionPoints;
    }

    private static void addVectorToList(List<Vector2> pool, Vector2[] newPoints) {
        for (Vector2 newPoint : newPoints) {
            boolean found = false;
            for (Vector2 poolPoint : pool) {
                {
                    if (newPoint.equals(poolPoint)) {
                        found = true;
                        break;
                    }
                }
                //Aici e nevoie de o optimizare cu disperare, dar asteptam;

            }
            if (!found) pool.add(newPoint);
        }
    }

    private static void combine2Lists(List<Vector2> pool, List<Vector2> newPoints) {
        for (Vector2 newPoint : newPoints) {
            boolean found = false;
            for (Vector2 poolPoint : pool) {
                {
                    if (newPoint.equals(poolPoint)) {
                        found = true;
                        break;
                    }
                }
                //Aici e nevoie de o optimizare cu disperare, dar asteptam;

            }
            if (!found) pool.add(newPoint);
        }
    }

   static public boolean IsPointInsidePoly(Vector2 test, ConvexPolygon2D poly)
    {
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = poly.vertices.length - 1; i < poly.vertices.length; j = i++)
        {
            float value = (poly.vertices[j].x - poly.vertices[i].x) * (test.y - poly.vertices[i].y) / (poly.vertices[j].y - poly.vertices[i].y) + poly.vertices[i].x;
            if ((poly.vertices[i].y > test.y) != (poly.vertices[j].y > test.y) && test.x < value ) {
                result = !result;
            }
        }
        return result;
    }

    static public Vector2[] GetIntersectionOfPolygons(ConvexPolygon2D poly1, ConvexPolygon2D poly2) {
        List<Vector2> clippedCorners = new ArrayList<Vector2>();

        //Add  the corners of poly1 which are inside poly2
        for (int i = 0; i < poly1.vertices.length; i++)
        {
            if (IsPointInsidePoly(poly1.vertices[i], poly2))
                addVectorToList(clippedCorners, new Vector2[] { poly1.vertices[i] });
        }

        //Add the corners of poly2 which are inside poly1
        for (int i = 0; i < poly2.vertices.length; i++)
        {
            if (IsPointInsidePoly(poly2.vertices[i],poly1))
                addVectorToList(clippedCorners, new Vector2[]{ poly2.vertices[i]});
        }

        List<Vector2> foundPoints = null;

        //Add  the intersection points
        for (int i = 0, next = 1; i < poly1.vertices.length; i++, next = (i + 1 == poly1.vertices.length) ? 0 : i + 1)
        {
            Line line1 = new Line( poly1.vertices[i], poly1.vertices[next] );

            foundPoints = GetIntersectionPoints(line1, poly2);

            combine2Lists(clippedCorners,foundPoints );

        }

        Vector2[] result = new Vector2[clippedCorners.size()];

        int i = 0;
        for( Vector2 point : clippedCorners){
            result[i] = point;
            i++;
        }
        //Does this really work ??
        return result;
    }


    Vector2[] OrderClockwise(Vector2[] points)
    {
        double mX = 0;
        double my = 0;
        for (Vector2 point : points){
            mX += point.x;
            my += point.y;
        }
        mX /= points.length;
        my /= points.length;

        //TODO
        //return points.OrderBy(v => Math.Atan2(v.Y - my, v.X - mX)).ToArray();
        return null;
    }


}