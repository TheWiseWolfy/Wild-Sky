package com.apetrei.engine.scenes.levels;

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

        gameContainer.getHudManager().addDialogueLine( new DialogLine("Unga bunga shunga lunga", 2f,1));
        gameContainer.getHudManager().addDialogueLine( new DialogLine("Yes, Sir !", 1.5f,0));

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

        if( gameContainer.getHudManager().isDialogueFinished() && hasHappened.contains(GlobalEvent.LEVEL2_COMPLETED)) {
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
        ob.setPlateToBuildAt( new Vector2(200, -350) );
        gameContainer.getObjectManager().addGameObject( ob.BackgroundBuilder("clouds.png", 1f, 0.4f) );

        //PLAYER
        ob.setPlateToBuildAt( new Vector2(-1300, 0) );
        gameContainer.getObjectManager().addGameObject( ob.PlayerBuilder() );

        initializeDetector();
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
                            gameContainer.getHudManager().addDialogueLine( new DialogLine("You did it you wonker !",2f,1));
                        }
                    }

                });
        detector.addComponent(projectileCollider);
        gameContainer.getObjectManager().addGameObject(detector);
    }


}
