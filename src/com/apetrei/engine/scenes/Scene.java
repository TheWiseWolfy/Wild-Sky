package com.apetrei.engine.scenes;

import com.apetrei.engine.GameContainer;

public interface Scene {

    public void init( );
    public void update(float frameTime);
    public void render();

}
