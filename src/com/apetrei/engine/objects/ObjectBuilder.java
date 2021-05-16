package com.apetrei.engine.objects;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.engine.ai.enemyAI;
import com.apetrei.providers.GameContainer;
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
        player.addComponent(new Rigidbody2D(plateToBuildAt, ConfigHandler.getPlayerMass() ));
        Collider2D collider = new ConvexCollider(false, ShapeProvider.getZepelinCollider() );
        player.addComponent(collider);
        player.addComponent(new TurretComponent(ObjectTag.ally ) );
        var animator = new AnimatedSpriteComponent("Airship",12);
        player.addComponent(animator);
        player.addComponent(new PlayerComponent());

        return  player;
    }

    public GameObject BackgroundBuilder(String name, float scale,float scrollFactor){
        GameObject background = new GameObject(gameContainer);
        background.addTag(ObjectTag.staticObject);
        background.addComponent(new TransformComponent(new Vector2(600, 600)));

        BackgroundSprite backSprite = new BackgroundSprite(name);
        backSprite.setScrollFactor(scrollFactor);
        backSprite.setSpriteScale(scale);
        background.addComponent(backSprite);

        return  background;
    }

    public  GameObject mediumEnemyBuilder(GameObject objective) {

        GameObject enemy = new GameObject(gameContainer);

        enemy.addComponent(new Rigidbody2D(plateToBuildAt, 5));

        Collider2D colider2 = new ConvexCollider(false, ShapeProvider.getZepelinCollider());
        enemy.addComponent(colider2);

        TurretComponent turretComponent = new TurretComponent(ObjectTag.enemy);
        turretComponent.setProjectileName("redProjectile");
        enemy.addComponent(turretComponent);

        enemy.addComponent(new SpriteComponent("enemy_airship_medium.png"));

        EnemyComponent enemyComponent = new EnemyComponent(100);
        enemyComponent.setEnemyEnginePower(30);

        enemyAI ai = new enemyAI(enemyComponent, gameContainer);
        if (objective != null){
            ai.setObjective(objective);
        }
        enemyComponent.setAi(ai);

        enemy.addComponent(enemyComponent);

        return  enemy;
    }

    public  GameObject lightEnemyBuilder(GameObject objective){

        GameObject enemy = new GameObject(gameContainer);

        enemy.addComponent(new Rigidbody2D( plateToBuildAt ,2));

        Collider2D colider2 = new ConvexCollider(false, ShapeProvider.getZepelinCollider());
        enemy.addComponent(colider2);

        TurretComponent turretComponent = new TurretComponent(ObjectTag.enemy );
        turretComponent.setProjectileName("redProjectile");
        enemy.addComponent(turretComponent);

        enemy.addComponent(new SpriteComponent("enemy_airship_light.png"));

        EnemyComponent enemyComponent = new EnemyComponent(60);
        enemyComponent.setEnemyEnginePower(40);

        enemyAI ai = new enemyAI(enemyComponent,gameContainer );
        ai.setObjective( objective);
        ai.setAttckDistance(400);
        enemyComponent.setAi(ai);

        enemy.addComponent(enemyComponent);

        return  enemy;
    }



    //__________________________________PROJECTILE___________________


    //__________________________________GETTER_________________________

    public void setPlateToBuildAt(Vector2 plateToBuildAt) {
        this.plateToBuildAt = plateToBuildAt;
    }
}
