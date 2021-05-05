package com.apetrei.engine.objects;

import com.apetrei.engine.GameContainer;
import com.apetrei.misc.exceptions.GameObjectNotFoundException;
import com.apetrei.misc.observers.ObjectManagerObserver;
import com.apetrei.misc.observers.PlayerObserver;

import java.util.ArrayList;
import java.util.List;


/*!
O clasa care mentine o lista de obicte din joc, si are abilitatea de a le serializa intr-un document, si de a le
restaora dintr-un document. In felul asta, starea interna a jocului poate fi capturat si stocat a.k.a quick save fuctionality
 */
public class ObjectManager {
    protected ArrayList<GameObject> gameObjects;
    protected ArrayList<GameObject> objectsOnHold;   //Obicte introduse in runtime care vor fi mutate in gameObjects intre cadre.
    protected GameContainer gameContainer;

    private List<ObjectManagerObserver> observers = new ArrayList<ObjectManagerObserver>();

    public ObjectManager(GameContainer gameContainer){
        gameObjects = new ArrayList<>();
        objectsOnHold = new ArrayList<>();
        this.gameContainer = gameContainer;
    }


    public void addGameObject(GameObject created){
       objectsOnHold.add(created);

       notifyoObservers(created);
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

    public GameObject findGameObject(ObjectTag tag) throws  GameObjectNotFoundException{
        //Cautam prin obiectele din joc
        for (var gameObject : gameObjects) {
            if( gameObject.hasTag( tag )){
                return gameObject;
            }
        }
        //Cautam prin obiectele care vor fi adaugate in joc

        for (var gameObject : objectsOnHold) {
            if( gameObject.hasTag( tag )){
                return gameObject;
            }
        }
        //Nu am gasit nimic
        throw new GameObjectNotFoundException( tag.toString() );
    }

   public void  resetObjectManager(){
        gameObjects.clear();
        objectsOnHold.clear();
    }

    //_________________________OBSERVER__________________

    final public void attachObserver( ObjectManagerObserver newObs){

        observers.add(newObs);
    }

    final public void dettach( PlayerObserver newObs){
        observers.remove(newObs);
    }

    final public void  notifyoObservers( GameObject newGameObject){
        for ( var obs :observers ) {
            obs.newObjectUpdate( newGameObject );
        }
    }
    //___________________________________________
}