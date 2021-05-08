package com.apetrei.engine.scenes;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.engine.GameContainer;
import com.apetrei.engine.input.InputType;
import com.apetrei.engine.objects.GameObject;
import com.apetrei.engine.objects.ObjectTag;
import com.apetrei.engine.objects.components.*;
import com.apetrei.engine.physics.primitives.colliders.ConvexCollider;
import com.apetrei.engine.renderer.ResourceLoader;
import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.misc.Vector2;
import com.apetrei.misc.exceptions.ResourceNotFoundException;
import com.apetrei.misc.observer.ObjectManagerObserver;
import com.apetrei.misc.observer.PlayerObserver;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GameplayScene implements Scene, PlayerObserver, ObjectManagerObserver {
    GameContainer gameContainer;

    boolean paused = false;
    BufferedImage background = null;
    BufferedImage sprite = null;

    public GameplayScene(GameContainer gameContainer) {
        this.gameContainer = gameContainer;

        try {
            background = ResourceLoader.getInstance().getSprite("Pause_menu_background.png");
            sprite = ResourceLoader.getInstance().getSprite("Button.png");

        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {

        gameContainer.getMenuManager().clearUI();
        gameContainer.getPhysicsSystem().resetPhysicsSystem();

        gameContainer.getObjectManager().resetObjectManager();
        gameContainer.getObjectManager().attachObserver(this);

        initializeGame(gameContainer);
        initializePauseMenu(gameContainer);
    }

    @Override
    public void update( float frameTime) {

        if( ConfigHandler.isDebugMode()) {
            if (gameContainer.getInput().isKey(KeyEvent.VK_F1, InputType.DOWN)) {
                gameContainer.goTo(new GameplayScene(gameContainer));
            }
            if (gameContainer.getInput().isKey(KeyEvent.VK_F2, InputType.DOWN)) {
                gameContainer.goBack();
            }
        }

        if (gameContainer.getInput().isKey(KeyEvent.VK_ESCAPE, InputType.DOWN)) {

            paused = !paused;
            if( ConfigHandler.isDebugMode()) {
                if (paused) {
                    System.out.println("The game has been paused.");
                } else if (!paused) {
                    System.out.println("The game has been unpaused.");
                }
            }
        }

        if (!paused) {
            //PHYSICS UPDATE
            gameContainer.getPhysicsSystem().updatePhysics(frameTime);
            //UPDATE
            gameContainer.getObjectManager().updateObjects(frameTime);
            //HUD UPDATE
        }else {
            gameContainer.getMenuManager().update();
        }
    }

    @Override
    public void render() {

        if (!paused) {
            gameContainer.getObjectManager().renderObjects();
            gameContainer.getHudManager().updateHUD();
        }else {
            gameContainer.getRenderer().getLayerRenderer().drawStaticSprite(new Vector2( ConfigHandler.getWidth()/2,ConfigHandler.getHeight()/2), 0.6f, background);
            gameContainer.getMenuManager().draw();
        }

    }

    private void initializePauseMenu(GameContainer gameContainer){

        //MENU BUTTON
        Vector2 button2Poz = new Vector2(ConfigHandler.getWidth() / 2, ConfigHandler.getHeight() / 2 -100);
        com.apetrei.engine.gui.UIElements.Button button2 = new com.apetrei.engine.gui.UIElements.Button("Continue",button2Poz, 0.3f, sprite, () -> {
            paused = false;
        });

        //MENU BUTTON
        Vector2 button1Poz = new Vector2(ConfigHandler.getWidth() / 2, ConfigHandler.getHeight() / 2 );
        com.apetrei.engine.gui.UIElements.Button button1 = new com.apetrei.engine.gui.UIElements.Button("Menu",button1Poz, 0.3f, sprite, () -> {
            gameContainer.goBack();
        });

        //SETTINGS BUTTON
        Vector2 button3Poz = new Vector2(ConfigHandler.getWidth() / 2, ConfigHandler.getHeight() / 2 + 100);
        com.apetrei.engine.gui.UIElements.Button button3 = new com.apetrei.engine.gui.UIElements.Button("Settings",button3Poz, 0.3f, sprite, () -> {
            System.out.println("Settings Button");
        });

        gameContainer.getMenuManager().addUIElement(button1);
        gameContainer.getMenuManager().addUIElement(button2);
        gameContainer.getMenuManager().addUIElement(button3);
    }

    private void initializeGame(GameContainer gameContainer) {

        List<Vector2> waka = new ArrayList<Vector2>();

        waka.add(new Vector2(-20, 50));
        waka.add(new Vector2(30, 50));
        waka.add(new Vector2(70, 0));
        waka.add(new Vector2(30, -50));
        waka.add(new Vector2(-30, -50));
        waka.add(new Vector2(-70, 0));

        ConvexPolygon2D wa = new ConvexPolygon2D(waka);

        //BACKGROUND
        GameObject background = new GameObject(gameContainer);
        background.addTag(ObjectTag.staticObject);
        background.addComponent(new TransformComponent(new Vector2(600, 600)));
        BackgroundSprite backSprite = new BackgroundSprite("Level1_background.png");
        backSprite.setScrollFactor(0.2f);
        background.addComponent(backSprite);
        gameContainer.getObjectManager().addGameObject(background);


        //DOCK
        GameObject dock = new GameObject(gameContainer);
        dock.addComponent(new Rigidbody2D(new Vector2(400, 0), 0));

        List<Vector2> waka2 = new ArrayList<Vector2>();

        waka2.add(new Vector2(-720, 200));
        waka2.add(new Vector2(631, 200));
        waka2.add(new Vector2(630, -301));
        waka2.add(new Vector2(-720, -300));

        ConvexPolygon2D wa2 = new ConvexPolygon2D(waka2);
        Collider2D colider4 = new ConvexCollider(false, wa2);
        dock.addComponent(colider4);
        BackgroundSprite sprite = new BackgroundSprite("Port.png");
        sprite.setSpriteScale(1f);
        dock.addComponent(sprite);
        gameContainer.getObjectManager().addGameObject(dock);

        //PLAYER
        GameObject gameObject1 = new GameObject(gameContainer);
        gameObject1.addComponent(new Rigidbody2D(new Vector2(200, 500), 1));
        Collider2D colider1 = new ConvexCollider(false, wa);
        gameObject1.addComponent(colider1);
        gameObject1.addComponent(new TurretComponent());
        gameObject1.addComponent(new PlayerComponent());
        gameObject1.addComponent(new SpriteComponent("Airship.png"));
        gameContainer.getObjectManager().addGameObject(gameObject1);

        /////////ENEMY
        GameObject gameObject2 = new GameObject(gameContainer);
        gameObject2.addComponent(new Rigidbody2D(new Vector2(600, 700), 5));
        Collider2D colider2 = new ConvexCollider(false, wa);
        gameObject2.addComponent(colider2);
        gameObject2.addComponent(new TurretComponent());
        gameObject2.addComponent(new SpriteComponent("Airship.png"));
        gameObject2.addComponent(new EnemyComponent(dock));
        gameContainer.getObjectManager().addGameObject(gameObject2);

        /////////ENEMY2
        GameObject gameObject3 = new GameObject(gameContainer);
        gameObject3.addComponent(new Rigidbody2D(new Vector2(600, 450), 5));
        Collider2D colider3 = new ConvexCollider(false, wa);
        gameObject3.addComponent(colider3);
        gameObject3.addComponent(new TurretComponent());
        gameObject3.addComponent(new SpriteComponent("Airship.png"));
        gameObject3.addComponent(new EnemyComponent(dock));

        gameContainer.getObjectManager().addGameObject(gameObject3);

        /////////ENEMY2
        GameObject gameObject4 = new GameObject(gameContainer);
        gameObject4.addComponent(new Rigidbody2D(new Vector2(100, 350), 5));
        Collider2D colider6 = new ConvexCollider(false, wa);
        gameObject4.addComponent(colider6);
        gameObject4.addComponent(new TurretComponent());
        gameObject4.addComponent(new SpriteComponent("Airship.png"));
        gameObject4.addComponent(new EnemyComponent(dock));

        gameContainer.getObjectManager().addGameObject(gameObject4);


    }

    //_______________________________OBSERVERS_______________________
    @Override
    public void playerUpdate(int engineLevel, int playerHealt) {
        if(playerHealt == 0){
            gameContainer.goBack();
        }
    }

    @Override
    public void newObjectUpdate(GameObject gameObject) {
        if(gameObject.hasTag(ObjectTag.player) ){
            try {
                PlayerComponent  pc = (PlayerComponent) gameObject.getComponent(PlayerComponent.class);
                pc.attach(this);
            } catch (Exception e) {
                System.err.println( "");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void objectDeletedUpdate(GameObject gameObject) {

    }
}
