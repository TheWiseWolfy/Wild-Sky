package com.apetrei.engine.components;

import com.apetrei.engine.GameObject;

//Asta e o reprezentare generica a unei componente, care atunci cand este imperecheata cu un gameObject, incapsuleaza perfect logica
//de suprafata a jocului
public abstract class Component {

    protected GameObject parent = null;

    public Component(){
    }

    //Lazi contructor - proceduri care trebuiesc facute odata ce mai multe componente au fost introduse in game object
    //Ex: Daca pui un rigidbody , si apoi un sprite, ultimul pas e sa le dai bind inpreuna dupa ce ambele contructoare au rulat deja
    public abstract void componentInit();

    public abstract void componentUpdate( double fT);

    public abstract void componentRender( );

    //______________________________SETTER AND GETTER_____________

    public GameObject getParent() {
        return parent;
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

}
