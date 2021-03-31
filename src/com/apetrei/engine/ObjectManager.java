package com.apetrei.engine;

import java.util.ArrayList;

public class ObjectManager {
    protected ArrayList<GameObject> gameObjects;

    ObjectManager(){
        gameObjects = new ArrayList<>();
    }

   public void addGameObject(GameObject created){
        gameObjects.add(created);
    }

    //Update fuctions

    public void updateObjects(double fT){

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