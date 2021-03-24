package com.apetrei.engine;

import java.util.LinkedList;

public abstract class GameObject {

    protected LinkedList<GameObject> children;

    public GameObject(){

    }
    public  abstract void update();
    public  abstract void render();
}
