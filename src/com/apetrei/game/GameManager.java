package com.apetrei.game;

import com.apetrei.engine.AbstractGame;
import com.apetrei.engine.GameContainer;
import com.apetrei.engine.Renderer;

public class GameManager extends AbstractGame {

    @Override
    public void update(GameContainer gc, float df) {

    }

    @Override
    public void render(GameContainer gc, Renderer renderer) {

    }

    //hehe
    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.start();
    }
}
