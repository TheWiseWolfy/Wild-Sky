package com.apetrei.engine.scenes.levels;

import com.apetrei.engine.GameContainer;
import com.apetrei.engine.event.GlobalEvent;
import com.apetrei.engine.objects.GameObject;
import com.apetrei.engine.objects.ObjectBuilder;
import com.apetrei.engine.objects.ObjectTag;
import com.apetrei.engine.objects.components.*;
import com.apetrei.engine.physics.ShapeProvider;
import com.apetrei.engine.physics.primitives.colliders.ConvexCollider;
import com.apetrei.engine.scenes.GameplayScene;
import com.apetrei.engine.sound.SoundManager;
import com.apetrei.misc.Vector2;

public class Level2 extends GameplayScene {

    public Level2(GameContainer gameContainer) {
        super(gameContainer);
    }

    private GameObject player;

    @Override
    public void init() {
        super.init();
        gameContainer.getRenderer().getCamera().setBounds(300,300,1700,1500);
        SoundManager.getInstance().playMusic("battle_music2.wav");
        initializeGame(gameContainer);

        //Dialogue
        line = "Spargeți blocada! Ajungeți pe cealaltă parte intregi! E un ordin.";
        playDialogue(line, "2_1_R.wav", 1);
        line =  "Domnilor, pretul libertații noastre va fi plătit în sange, dar nu sangele nostru. ";
        playDialogue(line, "2_1_I.wav", 0);
        line =  "Nu lasati nava să treacă! ";
        playDialogue(line, "2_1_W.wav", 2);
    }

    @Override
    public void update(float frameTime) {
        super.update(frameTime);

        //EVENTS
        if( gameContainer.getGlobalEventQueue().checkCurrentEvent() == GlobalEvent.PLAYER_DESTROYED){
            gameContainer.goBack();
        }

        if( gameContainer.getHudManager().getDialogManager().isDialogueFinished() && hasHappened.contains(GlobalEvent.LEVEL2_COMPLETED)) {
            gameContainer.goBack();
        }
    }

    @Override
    public void render() {
        super.render();
    }

    private void initializeGame(GameContainer gameContainer) {

        //BACKGROUND
        gameContainer.getObjectManager().addGameObject( ob.BackgroundBuilder("Level2_background.png", 0.7f,0.2f) );
        ob.setPlateToBuildAt( new Vector2(0, -550) );
        gameContainer.getObjectManager().addGameObject( ob.BackgroundBuilder("clouds.png", 1f, 0.4f) );

        //PLAYER
        ob.setPlateToBuildAt( new Vector2(-1300, 0) );
        gameContainer.getObjectManager().addGameObject(player = ob.PlayerBuilder());

        //DETECTOR
        initializeDetector();

        ob.setPlateToBuildAt( new Vector2(200, 100));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( player) );
        ob.setPlateToBuildAt( new Vector2(200, 200));
       gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( player) );


        ob.setPlateToBuildAt( new Vector2(1600, 0));
        gameContainer.getObjectManager().addGameObject(   ob.lightEnemyBuilder( player) );

        ob.setPlateToBuildAt( new Vector2(2200, -100));
        gameContainer.getObjectManager().addGameObject(   ob.lightEnemyBuilder( player) );
        ob.setPlateToBuildAt( new Vector2(2200, 0));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( player) );

        ob.setPlateToBuildAt( new Vector2(2700, 100));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( player) );
        ob.setPlateToBuildAt( new Vector2(2700, -100));
        gameContainer.getObjectManager().addGameObject(   ob.lightEnemyBuilder( player) );
        ob.setPlateToBuildAt( new Vector2(2700, 0));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( player) );

        ob.setPlateToBuildAt( new Vector2(3200, -100));
        gameContainer.getObjectManager().addGameObject(   ob.lightEnemyBuilder( player) );
        ob.setPlateToBuildAt( new Vector2(3200, 0));
        gameContainer.getObjectManager().addGameObject(   ob.lightEnemyBuilder( player) );
        ob.setPlateToBuildAt( new Vector2(3200, 100));
        gameContainer.getObjectManager().addGameObject(   ob.lightEnemyBuilder( player) );
        ob.setPlateToBuildAt( new Vector2(3200, -100));
        gameContainer.getObjectManager().addGameObject(   ob.lightEnemyBuilder( player) );

        ob.setPlateToBuildAt( new Vector2(3500, 0));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( player) );
        ob.setPlateToBuildAt( new Vector2(3500, -200));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( player) );
        ob.setPlateToBuildAt( new Vector2(3500, 100));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( player) );


        ob.setPlateToBuildAt( new Vector2(3600, -100));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( player) );

        ob.setPlateToBuildAt( new Vector2(4000, 0));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( player) );
        ob.setPlateToBuildAt( new Vector2(4000, -100));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( player) );
    }

    private void initializeDetector( ){
        //Create detector
        GameObject detector = new GameObject(gameContainer);
        detector.addTag(ObjectTag.ignoreProjectile);

        //RIGIDBODY
        Rigidbody2D rigid = new Rigidbody2D(new Vector2(2600,0), 0.1f);
        detector.addComponent(rigid);
        //COLLIDER
        Collider2D projectileCollider = new ConvexCollider(true, ShapeProvider.getFinishLineCollider(),
                (Collider2D collider) -> {

                    if (collider.getParent().hasTag(ObjectTag.player)) {
                        gameContainer.getGlobalEventQueue().declareEvent( GlobalEvent.LEVEL2_COMPLETED);

                        //WIN CONDITION
                        if( !hasHappened.contains( GlobalEvent.LEVEL2_COMPLETED) ){
                            hasHappened.add( GlobalEvent.LEVEL2_COMPLETED);

                            line =  "Cum au reușit să scape? Sunt inconjurat de incompetenti!";
                            playDialogue(line, "2_2_W.wav", 2);
                        }
                    }

                });
        detector.addComponent(projectileCollider);
        gameContainer.getObjectManager().addGameObject(detector);
    }
}
