package com.apetrei.misc;

public class ExtraMath {
    public  static void rotateVec(Vector2 vec,float angleDeg, Vector2 origin){
        float x = vec.x - origin.x;
        float y = vec.y - origin.y;

        float cos = (float) Math.cos(Math.toRadians(angleDeg));
        float sin = (float) Math.sin(Math.toRadians(angleDeg));

        float xPrime = x * cos - y * sin;
        float yPrime = x* sin + y*cos;

        xPrime += origin.x;
        yPrime += origin.y;

        vec.x = xPrime;
        vec.y = yPrime;
    }

    public static boolean vectorIsNotNull( Vector2 vector2){
        if( !equal(vector2.x, 0) && !equal(vector2.y, 0) ){
            return true;
        }
        return false;
    }

    //http://realtimecollisiondetection.net/blog/?p=89
    public static boolean equal(float x, float y, float epsilon){
        return  Math.abs( x-y) <= epsilon * Math.max(1.0f,Math.max(Math.abs(x),Math.abs(y)  )  );
    }

    public static boolean equal(float x, float y){
        return  Math.abs( x-y) <= Float.MIN_VALUE * Math.max(1.0f,Math.max(Math.abs(x),Math.abs(y)  )  );
    }

    public static boolean equal(double x, double y){
        return  Math.abs( x-y) <= Float.MIN_VALUE * Math.max(1.0f,Math.max(Math.abs(x),Math.abs(y)  )  );
    }

    public static boolean compareVectors(Vector2 vec1,Vector2 vec2, float epsilon){
        return equal(vec1.x, vec2.x, epsilon) &&  equal(vec1.y, vec2.y, epsilon);

    }

    public static boolean compareVectors(Vector2 vec1,Vector2 vec2){
        return equal(vec1.x, vec2.x) &&  equal(vec1.y, vec2.y);

    }
}
