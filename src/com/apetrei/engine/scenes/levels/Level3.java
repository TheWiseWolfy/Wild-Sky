package com.apetrei.engine.scenes.levels;

import com.apetrei.engine.GameContainer;
import com.apetrei.engine.event.GlobalEvent;
import com.apetrei.engine.gui.DialogLine;
import com.apetrei.engine.objects.ObjectBuilder;

import com.apetrei.engine.scenes.GameplayScene;
import com.apetrei.misc.Vector2;

import java.util.Set;
import java.util.TreeSet;

public class Level3 extends GameplayScene {
    Set<GlobalEvent> hasHappened= new TreeSet<GlobalEvent>();

    public Level3(GameContainer gameContainer) {
        super(gameContainer);
    }

    @Override
    public void init() {
        super.init();

        gameContainer.getHudManager().addDialogueLine( new DialogLine("Unga bunga shunga lunga", 2f,1));
        gameContainer.getHudManager().addDialogueLine( new DialogLine("Yes, Sir !", 1.5f,0));

        initializeGame(gameContainer);
    }

    @Override
    public void update(float frameTime) {
        super.update(frameTime);

        //EVENTS
        if( gameContainer.getGlobalEventQueue().checkCurrentEvent() == GlobalEvent.PLAYER_DESTROYED ||
                gameContainer.getGlobalEventQueue().checkCurrentEvent() == GlobalEvent.OBJECTIVE_DESTROYED ){
            gameContainer.goBack();
        }

    }

    @Override
    public void render() {
        super.render();
    }

    private void initializeGame(GameContainer gameContainer) {
        ObjectBuilder ob = new ObjectBuilder( gameContainer);
        //BACKGROUND
        gameContainer.getObjectManager().addGameObject( ob.BackgroundBuilder("Level3_background.png", 0.7f) );

        ob.setPlateToBuildAt( new Vector2(200, -350) );
        gameContainer.getObjectManager().addGameObject( ob.PlayerBuilder() );
    }

}
