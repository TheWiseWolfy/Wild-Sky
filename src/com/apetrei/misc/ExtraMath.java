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

    //http://realtimecollisiondetection.net/blog/?p=89
    public static boolean compare(float x,float y, float epsilon){
        return  Math.abs( x-y) <= epsilon * Math.max(1.0f,Math.max(Math.abs(x),Math.abs(y)  )  );
    }

    public static boolean compare(float x,float y){
        return  Math.abs( x-y) <= Float.MIN_VALUE * Math.max(1.0f,Math.max(Math.abs(x),Math.abs(y)  )  );
    }

    public static boolean compare(double x,double y){
        return  Math.abs( x-y) <= Float.MIN_VALUE * Math.max(1.0f,Math.max(Math.abs(x),Math.abs(y)  )  );
    }

    public static boolean compareVectors(Vector2 vec1,Vector2 vec2, float epsilon){
        return compare(vec1.x, vec2.x, epsilon) &&  compare(vec1.y, vec2.y, epsilon);

    }

    public static boolean compareVectors(Vector2 vec1,Vector2 vec2){
        return compare(vec1.x, vec2.x) &&  compare(vec1.y, vec2.y);

    }
}
