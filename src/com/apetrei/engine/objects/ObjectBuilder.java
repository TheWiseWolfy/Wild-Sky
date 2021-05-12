package com.apetrei.engine.objects;

import com.apetrei.engine.GameContainer;
import com.apetrei.engine.objects.components.*;
import com.apetrei.engine.physics.ShapeProvider;
import com.apetrei.engine.physics.primitives.colliders.ConvexCollider;
import com.apetrei.misc.Vector2;

public class ObjectBuilder {
    private GameContainer gameContainer;

    private Vector2 plateToBuildAt = new Vector2(0,0);

    public ObjectBuilder(GameContainer gameContainer){
        this.gameContainer = gameContainer;
    }

    public GameObject PlayerBuilder(){

        GameObject player = new GameObject(gameContainer);
        player.addComponent(new Rigidbody2D(plateToBuildAt, 1));
        Collider2D collider = new ConvexCollider(false, ShapeProvider.getZepelinColider() );
        player.addComponent(collider);
        player.addComponent(new TurretComponent(ObjectTag.ally ) );
        player.addComponent(new PlayerComponent());
        player.addComponent(new SpriteComponent("Airship.png"));

        return  player;
    }

    public GameObject BackgroundBuilder(String name, float scale){
        GameObject background = new GameObject(gameContainer);
        background.addTag(ObjectTag.staticObject);
        background.addComponent(new TransformComponent(new Vector2(600, 600)));

        BackgroundSprite backSprite = new BackgroundSprite(name);
        backSprite.setScrollFactor(0.2f);
        backSprite.setSpriteScale(scale);
        background.addComponent(backSprite);

        return  background;
    }

    public  GameObject mediumEnemyBuilder(GameObject objective){

        GameObject enemy = new GameObject(gameContainer);
        enemy.addComponent(new Rigidbody2D( plateToBuildAt , 5));
        Collider2D colider2 = new ConvexCollider(false, ShapeProvider.getZepelinColider());
        enemy.addComponent(colider2);
        enemy.addComponent(new TurretComponent(ObjectTag.enemy ));
        enemy.addComponent(new SpriteComponent("Airship.png"));
        enemy.addComponent(new EnemyComponent(objective));

        return  enemy;
    }

    //__________________________________GETTER_________________________

    public void setPlateToBuildAt(Vector2 plateToBuildAt) {
        this.plateToBuildAt = plateToBuildAt;
    }
}
