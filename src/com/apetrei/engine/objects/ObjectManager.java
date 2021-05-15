package com.apetrei.engine.objects;

import com.apetrei.providers.GameContainer;
import com.apetrei.engine.objects.components.TransformComponent;
import com.apetrei.misc.Vector2;
import com.apetrei.misc.exceptions.ComponentMissingException;
import com.apetrei.misc.observer.ObjectManagerObserver;
import com.apetrei.misc.observer.PlayerObserver;

import java.util.ArrayList;
import java.util.List;


/*!
O clasa care mentine o lista de obicte din joc, si are abilitatea de a le serializa intr-un document, si de a le
restaora dintr-un document. In felul asta, starea interna a jocului poate fi capturat si stocat a.k.a quick save fuctionality
 */
public class ObjectManager {
    protected List<GameObject> gameObjects;
    protected List<GameObject> objectsOnHold;   //Obicte introduse in runtime care vor fi mutate in gameObjects intre cadre.
    protected List<GameObject> objectsToDelete;   //Obicte introduse in runtime care vor fi mutate in gameObjects intre cadre.

    protected GameContainer gameContainer;

    private List<ObjectManagerObserver> observers = new ArrayList<ObjectManagerObserver>();

    public ObjectManager(GameContainer gameContainer){
        gameObjects = new ArrayList<>();
        objectsOnHold = new ArrayList<>();
        objectsToDelete = new ArrayList<>();
        this.gameContainer = gameContainer;
    }


    public void addGameObject(GameObject created){
       objectsOnHold.add(created);
       notifyObserversOfNewObject(created);
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
            }else {
                objectsToDelete.add( current);
            }
        }

        for ( var current : objectsToDelete) {
            gameObjects.remove(current );
            notifyObserversOfDeletedObject(current);
        }
        objectsToDelete.clear();
    }

   public void  resetObjectManager(){
        gameObjects.clear();
        objectsOnHold.clear();
    }

    //_________________________UTILITY___________________

    //Might be used later
    /*
    public GameObject findGameObject(ObjectTag tag) throws  GameObjectNotFoundException{
        //Cautam prin obiectele din joc
        for (var gameObject : gameObjects) {
            if( gameObject.hasTag( tag )){
                return gameObject;
            }
        }
        //Nu am gasit nimic
        throw new GameObjectNotFoundException( tag.toString() );
    }
     */

    public List<GameObject> findGameObjectInRange( GameObject subject, int range ){

        assert ( subject.hasComponent( TransformComponent.class  ));
        List<GameObject> objectsFound = new ArrayList<GameObject>();

        //Cautam prin obiectele din joc
        for (var found : gameObjects) {
            if( !found.hasTag(ObjectTag.staticObject) && subject != found && found.hasComponent(TransformComponent.class )){
                TransformComponent transformSub = null;
                TransformComponent transformFound = null;

                try {
                    transformSub =(TransformComponent) subject.getComponent( TransformComponent.class );
                    transformFound =(TransformComponent) found.getComponent( TransformComponent.class );
                } catch (ComponentMissingException e) {
                    System.err.println("findGameObjectInRange fuction tried to get a transform and failed.");
                    e.printStackTrace();
                }

                Vector2 pozSubject = new Vector2(transformSub.getPosition() );
                Vector2 pozFound = new Vector2(transformFound.getPosition() );



              if(( pozFound.sub( pozSubject) ).getMagnitue() < range/2 ){
                  objectsFound.add(found);
              }
            }
        }
        return objectsFound;
    }

    //_________________________OBSERVER__________________

    final public void attachObserver( ObjectManagerObserver newObs){

        observers.add(newObs);
    }

    final public void dettach( PlayerObserver newObs){
        observers.remove(newObs);
    }

    final public void  notifyObserversOfNewObject( GameObject newGameObject){
        for ( var obs :observers ) {
            obs.newObjectUpdate( newGameObject );
        }
    }

    final public void  notifyObserversOfDeletedObject( GameObject newGameObject){
        for ( var obs :observers ) {
            obs.objectDeletedUpdate( newGameObject );
        }
    }
    //___________________________________________
}