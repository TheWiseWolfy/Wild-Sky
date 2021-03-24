package com.apetrei.engine;

public abstract class AbstractGame {
    public abstract void update(GameContainer gc, float df);
    public abstract void render(GameContainer gc, Renderer renderer);
}
