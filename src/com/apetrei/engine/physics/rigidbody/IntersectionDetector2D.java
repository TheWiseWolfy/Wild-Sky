package com.apetrei.engine.physics.rigidbody;

import com.apetrei.misc.AABB;
import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.misc.Line;
import com.apetrei.misc.ExtraMath;
import com.apetrei.misc.Vector2;

import java.util.ArrayList;
import java.util.List;

public class IntersectionDetector2D {


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

        for (int i = 0; i < poly.getVertices().size(); i++) {
            int next = (i + 1 == poly.getVertices().size()) ? 0 : i + 1;  //TODO Poate module e mai rapid ca asta ??

            Vector2 ip = GetIntersectionPoint(line1, new Line(poly.getVertices().get(i), poly.getVertices().get(next)) );

            if (ip != null) intersectionPoints.add(ip);
        }
        return intersectionPoints;
    }

   static public boolean IsPointInsidePoly(Vector2 test, ConvexPolygon2D poly)
    {
        int i,j;
        boolean result = false;
        List<Vector2> vertices = poly.getVertices();

        for (i = 0, j = vertices.size() - 1; i < vertices.size(); j = i++)
        {
            float value = ( vertices.get(j).x - vertices.get(i).x ) *
                          ( test.y - vertices.get(i).y ) /
                          ( vertices.get(j).y - vertices.get(i).y ) +
                           vertices.get(i).x;

            if ( ( vertices.get(i).y > test.y) != ( vertices.get(j).y > test.y) && test.x < value ) {
                result = !result;
            }
        }
        return result;
    }

    //O fuctie care returneaza toate varfurile care se suprapun a 2 poligoane
    static public  List<Vector2> getPointsInColision(ConvexPolygon2D poly1, ConvexPolygon2D poly2) {
        List<Vector2> clippedCorners = new ArrayList<Vector2>();

        //Add  the corners of poly1 which are inside poly2
        for (int i = 0; i < poly1.getVertices().size(); i++) {

            Vector2 point = poly1.getVertices().get(i);
            if (IsPointInsidePoly(point , poly2)  && !containsPoint(clippedCorners, point  ) ) {
                clippedCorners.add( point );
            }
        }
        //Add the corners of poly2 which are inside poly1
        for (int i = 0; i < poly2.getVertices().size(); i++) {

            Vector2 point = poly2.getVertices().get(i);
            if (IsPointInsidePoly(point,poly1) && !containsPoint(clippedCorners, point  ) ) {
                clippedCorners.add( point );
            }
        }
        return (clippedCorners);
    }

    //O fuctie care returneaza puctele unde se intersecteaza liniile a 2 poligoane
    static public List<Vector2> getPointsOfIntersectionFront(ConvexPolygon2D poly1, ConvexPolygon2D poly2) {
        List<Vector2> intersectionPoint = new ArrayList<Vector2>();

        //Add  the intersection points
        for (int i = 0, next = 1; i < poly1.getVertices().size(); i++, next = (i + 1 == poly2.getVertices().size()) ? 0 : i + 1) {
            Line line1 = new Line( poly1.getVertices().get(i), poly1.getVertices().get(next));

            List<Vector2> foundPoints =  GetIntersectionPoints(line1, poly2);
            if( !foundPoints.isEmpty() )
                intersectionPoint.addAll(foundPoints );
        }
        return intersectionPoint;
    }


    //O fuctie menita pentru optimizare
    public static boolean AABBAndAABBB(AABB b1, AABB b2) {
            //TODO This is needed for optimization
        return true;
    }


    //UTILITY FUCTIONS
    private static boolean containsPoint(List<Vector2> pool, Vector2 point) {
            for (Vector2 poolPoint : pool) {
                {
                    if (point.equals(poolPoint)) {
                        return true;
                    }
                }
            }
        return false;
    }
}