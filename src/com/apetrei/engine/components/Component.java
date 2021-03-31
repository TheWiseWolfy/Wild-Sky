package com.apetrei.engine.components;

import com.apetrei.engine.GameObject;

public abstract class Component {
    protected GameObject parent;


    public Component(GameObject _parent){
        parent = _parent;
    }

    public abstract void componentUpdate( double fT);
    public abstract void componentRender( );
}
