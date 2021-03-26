package com.apetrei.game;

import com.apetrei.engine.GameContainer;
import com.apetrei.engine.GameObject;
import com.apetrei.engine.Renderer;
import com.apetrei.engine.components.Drawing;
import com.apetrei.engine.components.SpriteComponent;
import com.apetrei.engine.components.TransformComponent;

public class GameManager {

    public static void main(String[] args) {
        GameContainer gameContainer = new GameContainer();

        GameObject wawawa = new GameObject();
        wawawa.addComponent(new TransformComponent(gameContainer,400,400) );
        wawawa.addComponent(new Drawing(gameContainer) );

        wawawa.addComponent(new SpriteComponent(gameContainer,(TransformComponent) wawawa.getComponent("com.apetrei.engine.components.TransformComponent"),"C:\\Users\\Lucian\\Desktop\\Projects\\Git\\Wild-Sky\\src\\main\\resources\\1fn6twsno4b61.png")  );
        gameContainer.getObjectManager().addGameObject(wawawa);

        gameContainer.start();


    }
}
