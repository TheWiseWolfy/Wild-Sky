package com.apetrei.engine;

import java.util.ArrayList;

public class ObjectManager {
    protected ArrayList<GameObject> gameObjects;
    GameContainer gameContainer;

    ObjectManager(GameContainer _gameContainer){
        gameContainer = _gameContainer;
        gameObjects = new ArrayList<>();
    }

    void addGameObject(GameObject created){
        gameObjects.add(created);
    }


    void updateObjects(){

        for( GameObject current : gameObjects ){
            current.update(gameContainer);

        }
    }

    void renderObjects(){
        for( GameObject current : gameObjects ){
            if(current.isActive() ) {
                current.render(gameContainer);
            }
        }
    }

}