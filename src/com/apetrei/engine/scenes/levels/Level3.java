package com.apetrei.engine.scenes.levels;

import com.apetrei.engine.GameContainer;
import com.apetrei.engine.event.GlobalEvent;
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
import com.apetrei.engine.sound.SoundManager;
import com.apetrei.misc.Vector2;

public class Level3 extends GameplayScene {
    public Level3(GameContainer gameContainer) {
        super(gameContainer);
    }

    private GameObject player;

    @Override
    public void init() {
        super.init();
        gameContainer.getRenderer().getCamera().setBounds(800,800,800,800);
        SoundManager.getInstance().playMusic("battle_music3.wav");
        initializeGame(gameContainer);

        line = "Acum e momentul nostru ne glorie. Distrugeti-le fortareata !";
        playDialogue(line, "3_1_R.wav", 1);
    }

    @Override
    public void update(float frameTime) {
        super.update(frameTime);

        //EVENTS
        if( gameContainer.getGlobalEventQueue().checkCurrentEvent() == GlobalEvent.PLAYER_DESTROYED ){
            gameContainer.goBack();
        }

        //EVENTS
        if( gameContainer.getGlobalEventQueue().checkCurrentEvent() == GlobalEvent.OBJECTIVE_DAMAGED && !hasHappened.contains(GlobalEvent.OBJECTIVE_DAMAGED )){
            hasHappened.add(GlobalEvent.OBJECTIVE_DAMAGED  );

            line = "Ne atacă! O să regreți că ai pus piciorul pe pămând Austriac. Doborați intrusul!";
            playDialogue(line, "3_2_W.wav", 2);
            line = "Singurul lucru pe care il regret e că nu l-am atacat mai devreme.";
            playDialogue(line, "3_2_I.wav", 0);
        }

        //WIN CONDITION
        if( gameContainer.getGlobalEventQueue().checkCurrentEvent() == GlobalEvent.OBJECTIVE_DESTROYED){
            gameContainer.getGlobalEventQueue().declareEvent(GlobalEvent.LEVEL3_COMPLETED);

            if(  !hasHappened.contains(GlobalEvent.LEVEL3_COMPLETED )) {
                hasHappened.add(GlobalEvent.LEVEL3_COMPLETED );
                line = " Inacceptabil! Chiar trebuie să fac eu totul ?";
                playDialogue(line, "3_3_W.wav", 2);
            }
        }

        if( gameContainer.getHudManager().getDialogManager().isDialogueFinished() && hasHappened.contains(GlobalEvent.LEVEL3_COMPLETED)) {
            gameContainer.goBack();
        }

        if( timePassed > 30){
            gameContainer.getGlobalEventQueue().declareEvent(GlobalEvent.LEVEL3_WAVE1);

            if( !hasHappened.contains(GlobalEvent.LEVEL3_WAVE1) ) {
                hasHappened.add(GlobalEvent.LEVEL3_WAVE1 );

                /////////ENEMY4
                ob.setPlateToBuildAt(new Vector2(500, 1500));
                gameContainer.getObjectManager().addGameObject(ob.lightEnemyBuilder(player));
                /////////ENEMY5
                ob.setPlateToBuildAt(new Vector2(600, 1500));
                gameContainer.getObjectManager().addGameObject(ob.mediumEnemyBuilder(player));

                /////////ENEMY6
                ob.setPlateToBuildAt(new Vector2(300, 0));
                gameContainer.getObjectManager().addGameObject(ob.lightEnemyBuilder(player));
                /////////ENEMY7
                ob.setPlateToBuildAt(new Vector2(300, 100));
                gameContainer.getObjectManager().addGameObject(ob.mediumEnemyBuilder(player));

                /////////ENEMY8
                ob.setPlateToBuildAt(new Vector2(1650, 700));
                gameContainer.getObjectManager().addGameObject(ob.mediumEnemyBuilder(player));
                /////////ENEMY9
                ob.setPlateToBuildAt(new Vector2(1650, 800));
                gameContainer.getObjectManager().addGameObject(ob.mediumEnemyBuilder(player));

                /////////ENEMY6
                ob.setPlateToBuildAt(new Vector2(700, 100));
                gameContainer.getObjectManager().addGameObject(ob.lightEnemyBuilder(player));
                /////////ENEMY7
                ob.setPlateToBuildAt(new Vector2(700, 200));
                gameContainer.getObjectManager().addGameObject(ob.mediumEnemyBuilder(player));
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
        gameContainer.getObjectManager().addGameObject( player = ob.PlayerBuilder() );

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
        gameContainer.getObjectManager().addGameObject(   ob.lightEnemyBuilder( player) );

        /////////ENEMY2
        ob.setPlateToBuildAt( new Vector2(600, 1400));
        gameContainer.getObjectManager().addGameObject(   ob.lightEnemyBuilder( player) );

        /////////ENEMY3
        ob.setPlateToBuildAt( new Vector2(400, 1400));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( player) );
    }

}
