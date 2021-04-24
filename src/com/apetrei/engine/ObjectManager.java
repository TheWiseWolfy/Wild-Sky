package com.apetrei.engine;

import com.apetrei.engine.components.*;
import com.apetrei.engine.physics.primitives.colliders.ConvexCollider;
import com.apetrei.engine.physics.rigidbody.Rigidbody2D;
import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.misc.Vector2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ObjectManager {
    protected ArrayList<GameObject> gameObjects;
    protected ArrayList<GameObject> objectsOnHold;
    protected GameContainer gameContainer;
    ObjectManager(GameContainer gameContainer){
        gameObjects = new ArrayList<>();
        objectsOnHold = new ArrayList<>();
        this.gameContainer = gameContainer;
    }

    public void saveGame() {
        try {
            FileOutputStream fout = new FileOutputStream("src/com/resources/levels/level1.save");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fout);
            //TEST SETUP
              /*
           ArrayList<GameObject> testList = new ArrayList<>()

            GameObject test = new GameObject();
            test.addComponent( new TransformComponent(new Vector2(600,600)));
            BackgroundSprite backSprite =  new BackgroundSprite("Level1_background.png");
            test.addComponent( backSprite);

            List<Vector2> waka =  new ArrayList<Vector2>();
            waka.add( new Vector2(-20 , 50))  ;
            waka.add( new Vector2(30 , 50) );
            waka.add( new Vector2(70 , 0) ) ;
            waka.add( new Vector2(30 , -50) ) ;
            waka.add( new Vector2(-30, -50) ) ;
            waka.add( new Vector2(-70, 0) );
            ConvexPolygon2D wa = new ConvexPolygon2D(waka);

            GameObject gameObject1 = new GameObject();
            gameObject1.addComponent(new Rigidbody2D( new Vector2(400,400),1) );
            Collider2D colider1 = new ConvexCollider( wa);
            gameObject1.addComponent(colider1);
            gameObject1.addComponent(new TurretComponent());
            gameObject1.addComponent(new PlayerComponent());
            gameObject1.addComponent(new SpriteComponent("Airship.png")  );
            gameContainer.getObjectManager().addGameObject(gameObject1);


            testList.add( test);
*/
            ///
            objectOutputStream.writeObject(gameObjects);
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