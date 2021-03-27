package com.apetrei.game;

import com.apetrei.engine.GameContainer;
import com.apetrei.engine.GameObject;
import com.apetrei.engine.components.PlayerComponent;
import com.apetrei.engine.components.SpriteComponent;
import com.apetrei.engine.components.TransformComponent;

public class GameManager {

    public static void main(String[] args) {
        GameContainer gameContainer = new GameContainer();

        GameObject wawawa = new GameObject(gameContainer);
        wawawa.addComponent(new TransformComponent(wawawa,800,800) );
        wawawa.addComponent(new PlayerComponent(wawawa));

        wawawa.addComponent(new SpriteComponent(wawawa,"C:\\Users\\Lucian\\Desktop\\Projects\\Git\\Wild-Sky\\src\\main\\resources\\79HAmr4.jpg")  );
        gameContainer.getObjectManager().addGameObject(wawawa);

        gameContainer.start();


    }
}
