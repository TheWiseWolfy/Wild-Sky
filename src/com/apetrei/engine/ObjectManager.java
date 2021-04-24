package com.apetrei.engine;

import java.io.*;
import java.util.ArrayList;


/*!
O clasa care mentine o lista de obicte din joc, si are abilitatea de a le serializa intr-un document, si de a le
restaora dintr-un document. In felul asta, starea interna a jocului poate fi capturat si stocat a.k.a quick save fuctionality
 */
public class ObjectManager {
    protected ArrayList<GameObject> gameObjects;
    protected ArrayList<GameObject> objectsOnHold;   //Obicte introduse in runtime care vor fi mutate in gameObjects intre cadre.
    protected GameContainer gameContainer;

    ObjectManager(GameContainer gameContainer){
        gameObjects = new ArrayList<>();
        objectsOnHold = new ArrayList<>();
        this.gameContainer = gameContainer;
    }

    //TODO: Extend fuctionality of loading system, create a level manager/ level editor mode.
    public void saveGame() {
        try {
            FileOutputStream fout = new FileOutputStream("src/com/resources/levels/level1.save");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fout);

            objectOutputStream.writeObject(gameObjects);

            System.out.println("Game saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restoreGame() {
        try {
            FileInputStream fin = new FileInputStream("src/com/resources/levels/level1.save");
            ObjectInputStream objectInputStream = new ObjectInputStream(fin);
            gameObjects = (ArrayList<GameObject>) objectInputStream.readObject();

            GameContainer.getInstance().getPhysicsSystem().physicsRefrsh(gameObjects);

            System.out.println("Game restored successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }

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