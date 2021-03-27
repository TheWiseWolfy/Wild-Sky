package com.apetrei.engine;

public abstract class Component {
    protected GameObject parent;


    public Component(GameObject _parent){
        parent = _parent;
    }

    public abstract void componentUpdate( double fT);
    public abstract void componentRender( );
}
