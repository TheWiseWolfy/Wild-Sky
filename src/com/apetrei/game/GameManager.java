package com.apetrei.game;

import com.apetrei.engine.GameContainer;
import com.apetrei.engine.GameObject;
import com.apetrei.engine.components.PlayerComponent;
import com.apetrei.engine.components.SpriteComponent;
import com.apetrei.engine.components.TransformComponent;
import com.apetrei.engine.components.Collider2D;
import com.apetrei.engine.physics.rigidbody.Rigidbody2D;
import com.apetrei.misc.Vector2;

public class GameManager {

    public static void main(String[] args) {
        GameContainer gameContainer = new GameContainer();

        GameObject gameObject1 = new GameObject(gameContainer);

        gameObject1.addComponent(new Rigidbody2D( new Vector2(800,800)) );

        gameObject1.addComponent(new Collider2D());
        gameObject1.addComponent(new PlayerComponent());

        gameObject1.addComponent(new SpriteComponent("C:\\Users\\Lucian\\Desktop\\Projects\\Git\\Wild-Sky\\src\\com\\resources\\79HAmr4.jpg")  );
        gameContainer.getObjectManager().addGameObject(gameObject1);


        gameContainer.start();


    }
}
