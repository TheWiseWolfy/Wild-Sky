package com.apetrei.game;

import com.apetrei.engine.GameContainer;
import com.apetrei.engine.GameObject;
import com.apetrei.engine.components.PlayerComponent;
import com.apetrei.engine.components.SpriteComponent;
import com.apetrei.engine.components.TransformComponent;
import com.apetrei.engine.components.Collider2D;
import com.apetrei.engine.physics.primitives.colliders.ConvexCollider;
import com.apetrei.engine.physics.rigidbody.Rigidbody2D;
import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.misc.Vector2;

public class GameManager {

    public static void main(String[] args) {
        GameContainer gameContainer = new GameContainer();

        GameObject gameObject1 = new GameObject(gameContainer);

        gameObject1.addComponent(new Rigidbody2D( new Vector2(800,800)) );

        Vector2[] waka = {
                new Vector2(0, 0),
                new Vector2(20, 20),
                new Vector2(30, 40),
                new Vector2(10, 60)
        };
        ConvexPolygon2D wa = new ConvexPolygon2D(waka);

        gameObject1.addComponent(new ConvexCollider( wa));
        gameObject1.addComponent(new PlayerComponent());

     //   gameObject1.addComponent(new SpriteComponent("C:\\Users\\Lucian\\Desktop\\Projects\\Git\\Wild-Sky\\src\\com\\resources\\79HAmr4.jpg")  );
        gameContainer.getObjectManager().addGameObject(gameObject1);


        /////////

        GameObject gameObject2 = new GameObject(gameContainer);

        gameObject2.addComponent(new Rigidbody2D( new Vector2(700,700)) );

        gameObject2.addComponent(new ConvexCollider( wa));

        //   gameObject1.addComponent(new SpriteComponent("C:\\Users\\Lucian\\Desktop\\Projects\\Git\\Wild-Sky\\src\\com\\resources\\79HAmr4.jpg")  );
        gameContainer.getObjectManager().addGameObject(gameObject2);



        gameContainer.start();


    }
}
