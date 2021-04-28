package com.apetrei.engine.gui;

import com.apetrei.engine.GameContainer;
import com.apetrei.engine.components.PlayerComponent;
import com.apetrei.misc.observers.PlayerObserver;

public class HUDManager implements PlayerObserver {

    private GameContainer gameContainer;

    public HUDManager(GameContainer gameContainer){
        this.gameContainer = gameContainer;



    }

    @Override
    public void update(int engineLevel) {
        System.out.println( engineLevel);
    }
}
