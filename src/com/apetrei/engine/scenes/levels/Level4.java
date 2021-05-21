package com.apetrei.engine.scenes.levels;

import com.apetrei.engine.GameContainer;
import com.apetrei.engine.event.GlobalEvent;
import com.apetrei.engine.objects.GameObject;
import com.apetrei.engine.objects.ObjectBuilder;
import com.apetrei.engine.scenes.GameplayScene;
import com.apetrei.engine.sound.SoundManager;
import com.apetrei.misc.Vector2;

public class Level4 extends GameplayScene {
    private int enemiesLeft;

    public Level4(GameContainer gameContainer) {
        super(gameContainer);
    }

    @Override
    public void init() {
        super.init();
        gameContainer.getRenderer().getCamera().setBounds(800,800,800,800);
        SoundManager.getInstance().playMusic("battle_music4.wav");
        initializeGame(gameContainer);


        line = "Zilele tale de tiranie s-au terminat!";
        playDialogue(line, "4_1_I.wav", 0);
        line = "Ce s-a terminat e mica ta rebeliune.";
        playDialogue(line, "4_1_W.wav", 2);
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
                line ="Cum e posibil ? Ce am făcut gresit ? Nu pot să creeeed...";
                playDialogue(line, "4_3_W.wav", 2);
                line = "O să ajuti lumea mai mult în moarte decât atunci cănd erai viață.";
                playDialogue(line, "4_3_I.wav", 0);
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
        enemiesLeft = 5;

        //BACKGROUND
        gameContainer.getObjectManager().addGameObject( ob.BackgroundBuilder("Level4_background.png", 0.7f,0.2f) );
        ob.setPlateToBuildAt( new Vector2(200, -350) );
        gameContainer.getObjectManager().addGameObject( ob.BackgroundBuilder("clouds.png", 1f, 0.4f) );

        //PLAYER
        ob.setPlateToBuildAt( new Vector2(0, 0) );
        GameObject player = ob.PlayerBuilder();
        gameContainer.getObjectManager().addGameObject(player );

        /////////ENEMY
        ob.setPlateToBuildAt( new Vector2(300, 1000));
        gameContainer.getObjectManager().addGameObject(   ob.lightEnemyBuilder( player) );

        /////////ENEMY
        ob.setPlateToBuildAt( new Vector2(1000, 1000));
        gameContainer.getObjectManager().addGameObject(   ob.lightEnemyBuilder( player) );

        /////////ENEMY
        ob.setPlateToBuildAt( new Vector2(800, 1600));
        gameContainer.getObjectManager().addGameObject(   ob.lightEnemyBuilder( player) );

        /////////ENEMY2
        ob.setPlateToBuildAt( new Vector2(400, -600));
        gameContainer.getObjectManager().addGameObject(   ob.lightEnemyBuilder( player) );

        /////////ENEMY3
        ob.setPlateToBuildAt( new Vector2(200, -600));
        gameContainer.getObjectManager().addGameObject(   ob.lightEnemyBuilder( player) );

        ob.setPlateToBuildAt(new Vector2( 500, 1200));
        gameContainer.getObjectManager().addGameObject( ob.heavyEnemyBuilder( player));
        enemiesLeft++;
    }

}
