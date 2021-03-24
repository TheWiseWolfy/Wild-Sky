package com.apetrei.engine;

public abstract class AbstractGame {
    public abstract void update(GameContainer gameContainer, double frameTime);
    public abstract void render(GameContainer gameContainer, Renderer renderer);
}
