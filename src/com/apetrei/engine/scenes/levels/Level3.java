package com.apetrei.engine.scenes.levels;

import com.apetrei.providers.GameContainer;
import com.apetrei.engine.event.GlobalEvent;
import com.apetrei.engine.gui.DialogLine;
import com.apetrei.engine.objects.GameObject;
import com.apetrei.engine.objects.ObjectBuilder;

import com.apetrei.engine.objects.ObjectTag;
import com.apetrei.engine.objects.components.BackgroundSprite;
import com.apetrei.engine.objects.components.Collider2D;
import com.apetrei.engine.objects.components.ObjectiveComponent;
import com.apetrei.engine.objects.components.Rigidbody2D;
import com.apetrei.engine.physics.ShapeProvider;
import com.apetrei.engine.physics.primitives.colliders.ConvexCollider;
import com.apetrei.engine.scenes.GameplayScene;
import com.apetrei.misc.Vector2;

public class Level3 extends GameplayScene {
    public Level3(GameContainer gameContainer) {
        super(gameContainer);
    }

    ObjectBuilder ob;

    @Override
    public void init() {
        super.init();
        gameContainer.getRenderer().getCamera().setBounds(800,800,800,800);
        ob = new ObjectBuilder( gameContainer);
        initializeGame(gameContainer);
    }

    @Override
    public void update(float frameTime) {
        super.update(frameTime);

        //EVENTS
        if( gameContainer.getGlobalEventQueue().checkCurrentEvent() == GlobalEvent.PLAYER_DESTROYED ){
            gameContainer.goBack();
        }
        //WIN CONDITION
        if( gameContainer.getGlobalEventQueue().checkCurrentEvent() == GlobalEvent.OBJECTIVE_DESTROYED){
            gameContainer.getGlobalEventQueue().declareEvent(GlobalEvent.LEVEL3_COMPLETED);
            hasHappened.add( GlobalEvent.LEVEL3_COMPLETED);
        }

        if( gameContainer.getHudManager().getDialogManager().isDialogueFinished() && hasHappened.contains(GlobalEvent.LEVEL3_COMPLETED)) {
            gameContainer.goBack();
        }

        if( timePassed > 20){
            gameContainer.getGlobalEventQueue().declareEvent(GlobalEvent.LEVEL3_WAVE1);

            if( !hasHappened.contains(GlobalEvent.LEVEL3_WAVE1) ) {
                /////////ENEMY4
                ob.setPlateToBuildAt(new Vector2(500, 1400));
                gameContainer.getObjectManager().addGameObject(ob.lightEnemyBuilder(null));
                /////////ENEMY5
                ob.setPlateToBuildAt(new Vector2(600, 1400));
                gameContainer.getObjectManager().addGameObject(ob.mediumEnemyBuilder(null));
                hasHappened.add(GlobalEvent.LEVEL3_WAVE1 );

                /////////ENEMY6
                ob.setPlateToBuildAt(new Vector2(500, 0));
                gameContainer.getObjectManager().addGameObject(ob.lightEnemyBuilder(null));
                /////////ENEMY7
                ob.setPlateToBuildAt(new Vector2(600, 0));
                gameContainer.getObjectManager().addGameObject(ob.mediumEnemyBuilder(null));
                hasHappened.add(GlobalEvent.LEVEL3_WAVE1 );

                /////////ENEMY8
                ob.setPlateToBuildAt(new Vector2(1750, 600));
                gameContainer.getObjectManager().addGameObject(ob.mediumEnemyBuilder(null));
                hasHappened.add(GlobalEvent.LEVEL3_WAVE1 );
                /////////ENEMY9
                ob.setPlateToBuildAt(new Vector2(1650, 1000));
                gameContainer.getObjectManager().addGameObject(ob.mediumEnemyBuilder(null));
                hasHappened.add(GlobalEvent.LEVEL3_WAVE1 );
            }
        }
    }

    @Override
    public void render() {
        super.render();
    }

    private void initializeGame(GameContainer gameContainer) {

        //BACKGROUND
        gameContainer.getObjectManager().addGameObject( ob.BackgroundBuilder("Level3_background.png", 0.7f,0.2f) );
        ob.setPlateToBuildAt( new Vector2(200, -350) );
        gameContainer.getObjectManager().addGameObject( ob.BackgroundBuilder("clouds.png", 1f, 0.4f) );

        //PLAYER
        ob.setPlateToBuildAt( new Vector2(200,800 ) );
        gameContainer.getObjectManager().addGameObject( ob.PlayerBuilder() );

        //ENEMY DOCK
        GameObject lighthouse = new GameObject(gameContainer);
        lighthouse.addComponent(new Rigidbody2D(new Vector2(1200, 800), 0));
        lighthouse.addTag(ObjectTag.enemy);
        Collider2D colider4 = new ConvexCollider(false, ShapeProvider.getLighthouseCollider());
        lighthouse.addComponent(colider4);
        BackgroundSprite sprite = new BackgroundSprite("Lighthouse.png");
        sprite.setSpriteScale(1.1f);
        lighthouse.addComponent(sprite);
        lighthouse.addComponent( new ObjectiveComponent(1000));
        gameContainer.getObjectManager().addGameObject(lighthouse);

        /////////ENEMY
        ob.setPlateToBuildAt( new Vector2(800, 1400));
        gameContainer.getObjectManager().addGameObject(   ob.lightEnemyBuilder( null) );

        /////////ENEMY2
        ob.setPlateToBuildAt( new Vector2(600, 1400));
        gameContainer.getObjectManager().addGameObject(   ob.lightEnemyBuilder( null) );

        /////////ENEMY3
        ob.setPlateToBuildAt( new Vector2(400, 1400));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( null) );

    }

}
