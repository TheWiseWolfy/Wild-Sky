package com.apetrei.engine.scenes.levels;

import com.apetrei.engine.GameContainer;
import com.apetrei.engine.event.GlobalEvent;
import com.apetrei.engine.gui.DialogLine;
import com.apetrei.engine.objects.GameObject;
import com.apetrei.engine.objects.ObjectBuilder;

import com.apetrei.engine.objects.ObjectTag;
import com.apetrei.engine.objects.components.BackgroundSprite;
import com.apetrei.engine.objects.components.Collider2D;
import com.apetrei.engine.objects.components.GameObjectiveComponent;
import com.apetrei.engine.objects.components.Rigidbody2D;
import com.apetrei.engine.physics.ShapeProvider;
import com.apetrei.engine.physics.primitives.colliders.ConvexCollider;
import com.apetrei.engine.scenes.GameplayScene;
import com.apetrei.misc.Vector2;

import java.util.Set;
import java.util.TreeSet;

public class Level3 extends GameplayScene {
    public Level3(GameContainer gameContainer) {
        super(gameContainer);
    }

    @Override
    public void init() {
        super.init();

        gameContainer.getHudManager().addDialogueLine( new DialogLine("Unga bunga shunga lunga", 2f,1));
        gameContainer.getHudManager().addDialogueLine( new DialogLine("Yes, Sir !", 1.5f,0));

        initializeGame(gameContainer);

        gameContainer.getRenderer().getCamera().setBounds(800,800,800,800);
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

        if( gameContainer.getHudManager().isDialogueFinished() && hasHappened.contains(GlobalEvent.LEVEL3_COMPLETED)) {
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
        gameContainer.getObjectManager().addGameObject( ob.BackgroundBuilder("Level3_background.png", 0.7f,0.2f) );
        ob.setPlateToBuildAt( new Vector2(200, -350) );
        gameContainer.getObjectManager().addGameObject( ob.BackgroundBuilder("clouds.png", 1f, 0.4f) );

        //PLAYER
        ob.setPlateToBuildAt( new Vector2(200, -350) );
        gameContainer.getObjectManager().addGameObject( ob.PlayerBuilder() );

        //ENEMY DOCK
        GameObject dock = new GameObject(gameContainer);
        dock.addComponent(new Rigidbody2D(new Vector2(400, -600), 0));
        dock.addTag(ObjectTag.enemy);
        Collider2D colider4 = new ConvexCollider(false, ShapeProvider.getDockCollider());
        dock.addComponent(colider4);
        BackgroundSprite sprite = new BackgroundSprite("Port.png");
        sprite.setSpriteScale(0.7f);
        dock.addComponent(sprite);
        dock.addComponent( new GameObjectiveComponent(5));
        gameContainer.getObjectManager().addGameObject(dock);

        /////////ENEMY
        ob.setPlateToBuildAt( new Vector2(800, 1000));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( dock) );

        /////////ENEMY2
        ob.setPlateToBuildAt( new Vector2(400, 1000));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( dock) );

        /////////ENEMY3
        ob.setPlateToBuildAt( new Vector2(200, 1000));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( dock) );

    }

}
