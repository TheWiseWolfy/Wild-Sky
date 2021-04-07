package com.apetrei.misc;

public class Vector2 {

    // Variables
    public float x;
    public float y;

    // Constructors
    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }
    //Actually useful fuctions

    public void normalize(){
        float magnitude = (float)Math.sqrt(Math.pow(x,2) + Math.pow(y,2));

            x = x/magnitude;
            y = y/magnitude;

       if( x == Float.NEGATIVE_INFINITY ||  x == Float.POSITIVE_INFINITY ) {
           throw new ArithmeticException();   //you screwed up
       }
    }

    public float dot(Vector2 v) {
        return this.x * v.x + this.y * v.y;
    }

    public Vector2 add(Vector2 v){
        this.x += v.x;
        this.y += v.y;

        return this;
    }

    public Vector2 sub(Vector2 v){
        this.x -= v.x;
        this.y -= v.y;

        return this;
    }

    public Vector2 mul(float s){
        this.x = this.x  * s;
        this.y = this.y  * s;

        return this;
    }

    public Vector2 mul(Vector2 v){
        this.x = this.x  * v.x;
        this.y = this.y  * v.y;
        return this;
    }

    public Vector2 max(Vector2 v){
        this.x = Math.max(this.x,v.x);
        this.y = Math.max(this.y,v.y);

        return this;
    }

    public Vector2 min(Vector2 v){

        this.x = Math.min(this.x,v.x);
        this.y = Math.min(this.y,v.y);

        return this;
    }

    public float lenghtSquared(){
        return (float)(Math.pow(x,2) + Math.pow(y,2));
    }

    //____________________FLUF______________________

    public float get(int i){
        if (i == 0){
            return x;
        }else if(i == 1) {
            return y;
        }
        return 0;
    }

    public void set(int x,int y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
    }

    public void setComponent(int i, float val) {
        if (i == 0){
           x = val;
        }else if(i == 1) {
           y = val;
        }
    }

    @Override
    public String toString() {
        return Float.toString(this.x) + ", " + Float.toString(this.y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Vector2)) {
            return false;
        }
        Vector2 v = (Vector2) obj;
        return ExtraMath.compare(v.x, this.x) && ExtraMath.compare(v.y, this.y);
    }
}