package com.apetrei.engine.scenes.levels;

import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.providers.GameContainer;
import com.apetrei.engine.event.GlobalEvent;
import com.apetrei.engine.gui.DialogLine;
import com.apetrei.engine.objects.GameObject;
import com.apetrei.engine.objects.ObjectBuilder;
import com.apetrei.engine.objects.ObjectTag;
import com.apetrei.engine.objects.components.*;
import com.apetrei.engine.physics.ShapeProvider;
import com.apetrei.engine.physics.primitives.colliders.ConvexCollider;
import com.apetrei.engine.scenes.GameplayScene;
import com.apetrei.misc.Vector2;

public class Level2 extends GameplayScene {

    public Level2(GameContainer gameContainer) {
        super(gameContainer);
    }

    @Override
    public void init() {
        super.init();
        initializeGame(gameContainer);
        gameContainer.getRenderer().getCamera().setBounds(300,300,1500,1500);

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

        ObjectBuilder ob = new ObjectBuilder( gameContainer);
        //BACKGROUND
        gameContainer.getObjectManager().addGameObject( ob.BackgroundBuilder("Level2_background.png", 0.7f,0.2f) );
        ob.setPlateToBuildAt( new Vector2(0, -350) );
        gameContainer.getObjectManager().addGameObject( ob.BackgroundBuilder("clouds.png", 1f, 0.4f) );

        //PLAYER
        ob.setPlateToBuildAt( new Vector2(-1300, 0) );
        GameObject player =ob.PlayerBuilder();
        gameContainer.getObjectManager().addGameObject( player );

        //DETECTOR
        initializeDetector();

        /////////ENEMY
        ob.setPlateToBuildAt( new Vector2(400, 100));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( player) );

        /////////ENEMY2
        ob.setPlateToBuildAt( new Vector2(500, 200));
       gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( player) );

        /////////ENEMY3
        ob.setPlateToBuildAt( new Vector2(700, 0));
        gameContainer.getObjectManager().addGameObject(   ob.lightEnemyBuilder( player) );

        /////////ENEMY4
        ob.setPlateToBuildAt( new Vector2(1600, 0));
        gameContainer.getObjectManager().addGameObject(   ob.lightEnemyBuilder( player) );

        /////////ENEMY5
        ob.setPlateToBuildAt( new Vector2(1600, 0));
        gameContainer.getObjectManager().addGameObject(   ob.lightEnemyBuilder( player) );

        /////////ENEMY5
        ob.setPlateToBuildAt( new Vector2(2200, 0));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( player) );

        /////////ENEMY5
        ob.setPlateToBuildAt( new Vector2(2300, 0));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( player) );

        /////////ENEMY5
        ob.setPlateToBuildAt( new Vector2(2700, 0));
        gameContainer.getObjectManager().addGameObject(   ob.lightEnemyBuilder( player) );

        /////////ENEMY5
        ob.setPlateToBuildAt( new Vector2(2800, 0));
        gameContainer.getObjectManager().addGameObject(   ob.lightEnemyBuilder( player) );

        /////////ENEMY5
        ob.setPlateToBuildAt( new Vector2(3200, 0));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( player) );

        /////////ENEMY5
        ob.setPlateToBuildAt( new Vector2(3500, 0));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( player) );
    }

    private void initializeDetector( ){
        //Create detector
        GameObject detector = new GameObject(gameContainer);
        //RIGIDBODY
        Rigidbody2D rigid = new Rigidbody2D(new Vector2(2600,0), 0.1f);
        detector.addComponent(rigid);
        //COLLIDER
        Collider2D projectileCollider = new ConvexCollider(true, ShapeProvider.getFinishLineCollider(),
                (Collider2D collider) -> {

                    if (collider.getParent().hasTag(ObjectTag.player)) {
                        gameContainer.getGlobalEventQueue().declareEvent( GlobalEvent.LEVEL2_COMPLETED);

                        if( !hasHappened.contains( GlobalEvent.LEVEL2_COMPLETED) ){
                            hasHappened.add( GlobalEvent.LEVEL2_COMPLETED);

                        }
                    }

                });
        detector.addComponent(projectileCollider);
        gameContainer.getObjectManager().addGameObject(detector);
    }


}
