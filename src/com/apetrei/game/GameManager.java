package com.apetrei.game;

import com.apetrei.engine.AbstractGame;
import com.apetrei.engine.GameContainer;
import com.apetrei.engine.Renderer;

public class GameManager extends AbstractGame {

    @Override
    public void update(GameContainer gameContainer, double ft) {

    }

    @Override
    public void render(GameContainer gameContainer, Renderer renderer) {

    }

    //hehe
    public static void main(String[] args) {
        GameContainer gameContainer = new GameContainer(new GameManager());
        gameContainer.start();
    }
}
