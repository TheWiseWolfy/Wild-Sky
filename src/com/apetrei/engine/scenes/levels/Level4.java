package com.apetrei.engine.scenes.levels;

import com.apetrei.providers.GameContainer;
import com.apetrei.engine.event.GlobalEvent;
import com.apetrei.engine.gui.DialogLine;
import com.apetrei.engine.objects.GameObject;
import com.apetrei.engine.objects.ObjectBuilder;
import com.apetrei.engine.scenes.GameplayScene;
import com.apetrei.misc.Vector2;

public class Level4 extends GameplayScene {
    private int enemiesLeft;

    public Level4(GameContainer gameContainer) {
        super(gameContainer);
    }

    @Override
    public void init() {
        super.init();

        gameContainer.getHudManager().getDialogManager().addDialogueLine( new DialogLine("Unga bunga shunga lunga", 2f,1));
        gameContainer.getHudManager().getDialogManager().addDialogueLine( new DialogLine("Yes, Sir !", 1.5f,0));

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

        if( gameContainer.getGlobalEventQueue().checkCurrentEvent() == GlobalEvent.ENEMY_DESTROYED ){
            enemiesLeft--;
        }

        //WIN CONDITION
        if( enemiesLeft == 0){
            gameContainer.getGlobalEventQueue().declareEvent( GlobalEvent.LEVEL4_COMPLETED);
            if( !hasHappened.contains( GlobalEvent.LEVEL4_COMPLETED) ) {
                gameContainer.getHudManager().getDialogManager().addDialogueLine(new DialogLine("You did it you wonker !", 2f, 1));
            }
            hasHappened.add( GlobalEvent.LEVEL4_COMPLETED);
        }

        //END LEVEL CONDITION
        if( gameContainer.getHudManager().getDialogManager().isDialogueFinished()  && hasHappened.contains(GlobalEvent.LEVEL4_COMPLETED)) {
            gameContainer.goBack();
        }

    }

    @Override
    public void render() {
        super.render();
    }

    private void initializeGame(GameContainer gameContainer) {

        enemiesLeft = 3;

        ObjectBuilder ob = new ObjectBuilder( gameContainer);
        //BACKGROUND
        gameContainer.getObjectManager().addGameObject( ob.BackgroundBuilder("Level4_background.png", 0.7f,0.2f) );
        ob.setPlateToBuildAt( new Vector2(200, -350) );
        gameContainer.getObjectManager().addGameObject( ob.BackgroundBuilder("clouds.png", 1f, 0.4f) );

        //PLAYER
        ob.setPlateToBuildAt( new Vector2(200, -350) );
        GameObject player = ob.PlayerBuilder();
        gameContainer.getObjectManager().addGameObject(player );

        /////////ENEMY
        ob.setPlateToBuildAt( new Vector2(800, 1000));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( player) );

        /////////ENEMY2
        ob.setPlateToBuildAt( new Vector2(400, 1000));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( player) );

        /////////ENEMY3
        ob.setPlateToBuildAt( new Vector2(200, 1000));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( player) );
    }

}
