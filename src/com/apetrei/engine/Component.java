package com.apetrei.engine;

public abstract class Component {
    GameContainer gameContainer;

   public Component(GameContainer _gameContainer){
        gameContainer = _gameContainer;
    }
    public abstract void componentUpdate(GameContainer gameContainer);
    public abstract void componentRender(GameContainer gameContainer);
}
