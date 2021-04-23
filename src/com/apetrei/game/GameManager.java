package com.apetrei.game;

import com.apetrei.engine.GameContainer;
import com.apetrei.engine.GameObject;
import com.apetrei.engine.components.*;
import com.apetrei.engine.physics.primitives.colliders.ConvexCollider;
import com.apetrei.engine.physics.rigidbody.Rigidbody2D;
import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.misc.Vector2;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    public static void main(String[] args) {
        GameContainer gameContainer = new GameContainer();

        List<Vector2> waka =  new ArrayList<Vector2>();
        waka.add( new Vector2(-20 , 50))  ;
        waka.add( new Vector2(30 , 50) );
        waka.add( new Vector2(70 , 0) ) ;
        waka.add( new Vector2(30 , -50) ) ;
        waka.add( new Vector2(-30, -50) ) ;
        waka.add( new Vector2(-70, 0) );
        ConvexPolygon2D wa = new ConvexPolygon2D(waka);

        //BACKGROUND
        GameObject background = new GameObject(gameContainer);
        background.addComponent( new TransformComponent(new Vector2(600,600)));
        BackgroundSprite backSprite =  new BackgroundSprite("Level1_background.png");
        backSprite.setScrollFactor(0.2f);
        background.addComponent( backSprite);
        gameContainer.getObjectManager().addGameObject(background);

        //PLAYER
        GameObject gameObject1 = new GameObject(gameContainer);
        gameObject1.addComponent(new Rigidbody2D( new Vector2(400,400),1) );
        Collider2D colider1 = new ConvexCollider( wa);
        gameObject1.addComponent(colider1);
        gameObject1.addComponent(new TurretComponent());
        gameObject1.addComponent(new PlayerComponent());
        gameObject1.addComponent(new SpriteComponent("Airship.png")  );
        gameContainer.getObjectManager().addGameObject(gameObject1);

        /////////

        GameObject gameObject2 = new GameObject(gameContainer);
        gameObject2.addComponent(new Rigidbody2D( new Vector2(600,600) ,5) );
        Collider2D colider2 = new ConvexCollider( wa);
        gameObject2.addComponent(colider2);
        gameObject2.addComponent(new SpriteComponent("Airship.png")  );
        gameContainer.getObjectManager().addGameObject(gameObject2);

        /////////
        GameObject gameObject3 = new GameObject(gameContainer);
        gameObject3.addComponent(new Rigidbody2D( new Vector2(600,300) ,5));
        Collider2D colider3 = new ConvexCollider( wa);
        gameObject3.addComponent(colider3);
        gameObject3.addComponent(new SpriteComponent("Airship.png")  );
        gameContainer.getObjectManager().addGameObject(gameObject3);


        //map element
        /////////
        GameObject dock = new GameObject(gameContainer);
        dock.addComponent(new Rigidbody2D( new Vector2(400,50) ,0));

        List<Vector2> waka2 =  new ArrayList<Vector2>();

        waka2.add( new Vector2(-720 , 200))  ;
        waka2.add( new Vector2(630 , 200) );
        waka2.add( new Vector2(630 , 0) ) ;
        waka2.add( new Vector2(630 , -300) ) ;
        waka2.add( new Vector2(-720, -300) ) ;
        waka2.add( new Vector2(-720, 0) );

        ConvexPolygon2D wa2 = new ConvexPolygon2D(waka2);

        Collider2D colider4 = new ConvexCollider( wa2);
        dock.addComponent(colider4);
        BackgroundSprite sprite = new BackgroundSprite("Port.png");
        sprite.setSpriteScale(1f);
        dock.addComponent(sprite );

        gameContainer.getObjectManager().addGameObject(dock);

        gameContainer.getPhysicsSystem().addColliders(colider4 );
        gameContainer.getPhysicsSystem().addColliders(colider3 );
        gameContainer.getPhysicsSystem().addColliders(colider2 );
        gameContainer.getPhysicsSystem().addColliders(colider1 );

        gameContainer.start();

    }
}
