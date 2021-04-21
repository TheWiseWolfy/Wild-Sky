package com.apetrei.engine;

import com.apetrei.engine.components.Collider2D;
import com.apetrei.engine.physics.PhysicsSystem2D;

import java.util.ArrayList;

public class ObjectManager {
    protected ArrayList<GameObject> gameObjects;
    protected ArrayList<GameObject> objectsOnHold;

    ObjectManager(){
        gameObjects = new ArrayList<>();
        objectsOnHold = new ArrayList<>();
    }

   public void addGameObject(GameObject created){
       objectsOnHold.add(created);
    }

    //Update fuctions

    public void updateObjects(float fT){

        gameObjects.addAll(objectsOnHold );
        objectsOnHold.clear();

        for( GameObject current : gameObjects ){
            if(current.isActive() ) {
                current.update( fT);
            }
        }
    }

    public void renderObjects(){
        for( GameObject current : gameObjects ){
            if(current.isActive() ) {
                current.render();
            }

        }
    }

}