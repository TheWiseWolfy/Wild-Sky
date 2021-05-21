package com.apetrei.engine.scenes.levels;

import com.apetrei.engine.sound.SoundManager;
import com.apetrei.engine.GameContainer;
import com.apetrei.engine.event.GlobalEvent;
import com.apetrei.engine.objects.GameObject;
import com.apetrei.engine.objects.ObjectBuilder;
import com.apetrei.engine.objects.ObjectTag;
import com.apetrei.engine.objects.components.*;
import com.apetrei.engine.physics.ShapeProvider;
import com.apetrei.engine.physics.primitives.colliders.ConvexCollider;
import com.apetrei.engine.scenes.GameplayScene;
import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.misc.Vector2;

public class Level1 extends GameplayScene {

    private int enemiesLeft = 0;
    int maxObjectiveHealt = 1000;

    String line;
    public Level1(GameContainer gameContainer) {
        super(gameContainer);
    }

    GameObject dock;

    @Override
    public void init() {
        super.init();
        gameContainer.getRenderer().getCamera().setBounds(800,800,800,800);
        SoundManager.getInstance().playMusic("battle_music.wav");
        initializeGame(gameContainer);

        //Dialogue
        line = "Domnilor, flota austriacă a ajuns în ușa noastră. Cred că e timpul să le  arătam cât de ospitalieri suntem. Protejați capitala cu orice cost!";
        playDialogue(line, "1_R.wav", 1);
        line = "Ințeles, amirale!";
        playDialogue(line, "1_I.wav", 0);

        //Enemies to kill
        enemiesLeft = 10;
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
            line = "Orasul este sub atac! Păstrați poziții defensive!";
            playDialogue(line, "2_R.wav", 1);
            line ="Uite o pată pe ecran. E o insectă sau e intreaga flotă Carpatiană?\n";
            playDialogue(line, "2_W.wav", 2);

            hasHappened.add(GlobalEvent.OBJECTIVE_DAMAGED);
        }

        if( gameContainer.getGlobalEventQueue().checkCurrentEvent() == GlobalEvent.ENEMY_DESTROYED ){
            enemiesLeft--;
        }
        //END LEVEL CONDITION
        if( gameContainer.getHudManager().getDialogManager().isDialogueFinished()  && hasHappened.contains(GlobalEvent.LEVEL1_COMPLETED)) {
            gameContainer.goBack();
        }

        if( timePassed > 40) {
            gameContainer.getGlobalEventQueue().declareEvent(GlobalEvent.LEVEL1_WAVE1);
            if( !hasHappened.contains(GlobalEvent.LEVEL1_WAVE1) ) {

                line = " Cu curaj! O altă flota se apropie din vest.";
                playDialogue(line, "4_R.wav", 1);
                line = "Toată lumea, ramaneti pe pozitie! Trageti doar la comanda mea. Astăzi ne apărăm patria!";
                playDialogue(line, "4_I.wav", 0);
                line = "Când o sa termin, nu o să mai rămână nimic de aparat.";
                playDialogue(line, "4_W.wav", 2);

                /////////ENEMY
                ob.setPlateToBuildAt(new Vector2(1800, 200));
                gameContainer.getObjectManager().addGameObject(ob.mediumEnemyBuilder(dock));

                /////////ENEMY2
                ob.setPlateToBuildAt(new Vector2(1800, 300));
                gameContainer.getObjectManager().addGameObject(ob.mediumEnemyBuilder(dock));

                /////////ENEMY3
                ob.setPlateToBuildAt(new Vector2(1800, 400));
                gameContainer.getObjectManager().addGameObject(ob.lightEnemyBuilder(dock));

                /////////ENEMY4
                ob.setPlateToBuildAt(new Vector2(1800, 500));
                gameContainer.getObjectManager().addGameObject(ob.lightEnemyBuilder(dock));

                /////////ENEMY4
                ob.setPlateToBuildAt(new Vector2(1800, 600));
                gameContainer.getObjectManager().addGameObject(ob.lightEnemyBuilder(dock));


                hasHappened.add(GlobalEvent.LEVEL1_WAVE1);
            }
        }

            //WIN CONDITION
        if( enemiesLeft == 0){
            gameContainer.getGlobalEventQueue().declareEvent( GlobalEvent.LEVEL1_COMPLETED);
            if( !hasHappened.contains( GlobalEvent.LEVEL1_COMPLETED) ){
                line = "Lașii! Asta o să ii invete minte să se puna cu Carpatia.";
                playDialogue(line, "5_R.wav", 1);
                line = "Nu o să fie ultima oară când auziți de mine.";
                playDialogue(line, "5_W.wav", 2);
            }
            hasHappened.add( GlobalEvent.LEVEL1_COMPLETED);
        }
    }

    @Override
    public void render() {
        super.render();
    }

    private void initializeGame(GameContainer gameContainer) {

        //BACKGROUND
        ob.setPlateToBuildAt( new Vector2(200, -350) );
        gameContainer.getObjectManager().addGameObject( ob.BackgroundBuilder("Level1_background.png", 0.7f,0.2f) );
        ob.setPlateToBuildAt( new Vector2(200, -350) );
        gameContainer.getObjectManager().addGameObject( ob.BackgroundBuilder("clouds.png", 1f, 0.4f) );

        //DOCK
        dock = new GameObject(gameContainer);
        dock.addComponent(new Rigidbody2D(new Vector2(400, -600), 0));
        dock.addTag(ObjectTag.ally);
        Collider2D colider4 = new ConvexCollider(false, ShapeProvider.getDockCollider());
        dock.addComponent(colider4);
        BackgroundSprite sprite = new BackgroundSprite("Port.png");
        sprite.setSpriteScale(0.7f);
        dock.addComponent(sprite);
        dock.addComponent( new ObjectiveComponent(maxObjectiveHealt));
        gameContainer.getObjectManager().addGameObject(dock);

        //Decorations
        ob.setPlateToBuildAt( new Vector2(100, 0)) ;
        gameContainer.getObjectManager().addGameObject( ob.baloonDecoration() );
        ob.setPlateToBuildAt( new Vector2(100, 300)) ;
        gameContainer.getObjectManager().addGameObject( ob.baloonDecoration() );

        ob.setPlateToBuildAt( new Vector2(800, 0)) ;
        gameContainer.getObjectManager().addGameObject( ob.baloonDecoration() );
        ob.setPlateToBuildAt( new Vector2(800, 300)) ;
        gameContainer.getObjectManager().addGameObject( ob.baloonDecoration() );

        //PLAYER
        ob.setPlateToBuildAt( new Vector2(200, -350) );
        gameContainer.getObjectManager().addGameObject( ob.PlayerBuilder() );

        /////////ENEMY
        ob.setPlateToBuildAt( new Vector2(700, 1000));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( dock) );

        /////////ENEMY2
        ob.setPlateToBuildAt( new Vector2(400, 1000));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( dock) );

        /////////ENEMY3
        ob.setPlateToBuildAt( new Vector2(100, 1000));
        gameContainer.getObjectManager().addGameObject(   ob.mediumEnemyBuilder( dock) );

        /////////ENEMY4
        ob.setPlateToBuildAt( new Vector2(100, 1000));
        gameContainer.getObjectManager().addGameObject(   ob.lightEnemyBuilder( dock) );

        /////////ENEMY5
        ob.setPlateToBuildAt( new Vector2(100, 1100));
        gameContainer.getObjectManager().addGameObject(   ob.lightEnemyBuilder( dock) );
    }
}
