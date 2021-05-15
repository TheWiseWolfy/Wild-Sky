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
import com.apetrei.engine.sound.SoundManager;
import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.misc.Vector2;

public class Level1 extends GameplayScene {
    private int enemiesLeft = 0;

    public Level1(GameContainer gameContainer) {
        super(gameContainer);
    }

    @Override
    public void init() {
        super.init();

        gameContainer.getHudManager().getDialogManager().addDialogueLine( new DialogLine("Defend the city at all cost.", 2f,1));
        gameContainer.getHudManager().getDialogManager().addDialogueLine( new DialogLine("Yes, Sir !", 1.5f,0));
        gameContainer.getHudManager().getDialogManager().addDialogueLine( new DialogLine("Fight me pretisor", 1.5f,0));

        initializeGame(gameContainer);
        SoundManager.getInstance().playSound("music3.wav");

        gameContainer.getRenderer().getCamera().setBounds(800,800,800,800);
    }

    @Override
    public void update(float frameTime) {
        super.update(frameTime);
        //EVENTS
        if( gameContainer.getGlobalEventQueue().checkCurrentEvent() == GlobalEvent.PLAYER_DESTROYED ||
                gameContainer.getGlobalEventQueue().checkCurrentEvent() == GlobalEvent.OBJECTIVE_DESTROYED ){
            gameContainer.goBack();
        }

        if( gameContainer.getGlobalEventQueue().checkCurrentEvent() == GlobalEvent.OBJECTIVE_DAMAGED && !hasHappened.contains(GlobalEvent.OBJECTIVE_DAMAGED )){
            gameContainer.getHudManager().getDialogManager().addDialogueLine( new DialogLine("The city is being attacked !",1f,1));
            hasHappened.add(GlobalEvent.OBJECTIVE_DAMAGED);
        }

        if( gameContainer.getGlobalEventQueue().checkCurrentEvent() == GlobalEvent.ENEMY_DESTROYED ){
            enemiesLeft--;
        }
        //END LEVEL CONDITION
        if( gameContainer.getHudManager().getDialogManager().isDialogueFinished()  && hasHappened.contains(GlobalEvent.LEVEL1_COMPLETED)) {
            gameContainer.goBack();
        }

        //WIN CONDITION
        if( enemiesLeft == 0){
            gameContainer.getGlobalEventQueue().declareEvent( GlobalEvent.LEVEL1_COMPLETED);
            if( !hasHappened.contains( GlobalEvent.LEVEL1_COMPLETED) ){
                gameContainer.getHudManager().getDialogManager().addDialogueLine( new DialogLine("You did it you wonker !",2f,1));
            }
            hasHappened.add( GlobalEvent.LEVEL1_COMPLETED);
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
        ob.setPlateToBuildAt( new Vector2(200, -350) );
        gameContainer.getObjectManager().addGameObject( ob.BackgroundBuilder("Level1_background.png", 0.7f,0.2f) );
        ob.setPlateToBuildAt( new Vector2(200, -350) );
        gameContainer.getObjectManager().addGameObject( ob.BackgroundBuilder("clouds.png", 1f, 0.4f) );

        //DOCK
        GameObject dock = new GameObject(gameContainer);
        dock.addComponent(new Rigidbody2D(new Vector2(400, -600), 0));
        dock.addTag(ObjectTag.ally);
        Collider2D colider4 = new ConvexCollider(false, ShapeProvider.getDockCollider());
        dock.addComponent(colider4);
        BackgroundSprite sprite = new BackgroundSprite("Port.png");
        sprite.setSpriteScale(0.7f);
        dock.addComponent(sprite);
        dock.addComponent( new GameObjectiveComponent(5000));
        gameContainer.getObjectManager().addGameObject(dock);


        //PLAYER
        ConvexPolygon2D wa = new ConvexPolygon2D(ShapeProvider.getZepelinCollider());
        gameContainer.getObjectManager().addGameObject( ob.PlayerBuilder() );

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
