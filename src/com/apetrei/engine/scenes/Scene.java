package com.apetrei.engine.scenes;

import com.apetrei.engine.GameContainer;

public interface Scene {

    public void update( GameContainer gameContainer, float frameTime);
    public void render(GameContainer gameContainer);

}
