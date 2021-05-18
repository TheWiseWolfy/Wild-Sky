package com.apetrei.engine.objects;

import com.apetrei.engine.providers.ConfigHandler;
import com.apetrei.engine.ai.enemyAI;
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
        player.addComponent(new Rigidbody2D(plateToBuildAt, ConfigHandler.getPlayerMass() ));
        Collider2D collider = new ConvexCollider(false, ShapeProvider.getZepelinCollider() );
        player.addComponent(collider);

        var turretComponent = new TurretComponent( ObjectTag.ally);
        turretComponent.setProjectileSpread(0.1f);
        player.addComponent( turretComponent );

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

    public  GameObject heavyEnemyBuilder(GameObject objective) {

        GameObject enemy = new GameObject(gameContainer);

        //RIGIDBODY & COLLIDER
        enemy.addComponent(new Rigidbody2D(plateToBuildAt, 8));
        Collider2D colider2 = new ConvexCollider(false, ShapeProvider.getZepelinCollider());
        enemy.addComponent(colider2);

        //TURRET COMPONENT
        TurretComponent turretComponent = new TurretComponent(ObjectTag.enemy);
        turretComponent.setCountdown(0.1f);
        turretComponent.setProjectileName("redProjectile");
        enemy.addComponent(turretComponent);

        //SPRITE COMPONENT
        enemy.addComponent(new SpriteComponent("enemy_airship_heavy.png"));

        //ENEMY COMPONENT
        EnemyComponent enemyComponent = new EnemyComponent(1000);
        enemyComponent.setEnemyEnginePower(150);
        enemyAI ai = new enemyAI(enemyComponent, gameContainer);
        ai.setFightRange(950);
        if (objective != null){
            ai.setObjective(objective);
        }
        enemyComponent.setAi(ai);
        enemy.addComponent(enemyComponent);
        //
        return  enemy;
    }



    public  GameObject mediumEnemyBuilder(GameObject objective) {

        GameObject enemy = new GameObject(gameContainer);

        //RIGIDBODY && COLLIDER
        enemy.addComponent(new Rigidbody2D(plateToBuildAt, 5));
        Collider2D colider2 = new ConvexCollider(false, ShapeProvider.getZepelinCollider());
        enemy.addComponent(colider2);

        //TURRET COMPONENTN
        TurretComponent turretComponent = new TurretComponent(ObjectTag.enemy);
        turretComponent.setCountdown(0.4f);
        turretComponent.setProjectileSpeed(800f);

        turretComponent.setProjectileName("redProjectile");
        enemy.addComponent(turretComponent);

        //SPRITE COMPONENT
        enemy.addComponent(new SpriteComponent("enemy_airship_medium.png"));

        //EMEMY COMPONENT
        EnemyComponent enemyComponent = new EnemyComponent(100);
        enemyComponent.setEnemyEnginePower(40);
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

        //RIGIDBODY & COMPONENT
        enemy.addComponent(new Rigidbody2D( plateToBuildAt ,2));
        Collider2D colider2 = new ConvexCollider(false, ShapeProvider.getZepelinCollider());
        enemy.addComponent(colider2);

        //TURRENT COMPONENT
        TurretComponent turretComponent = new TurretComponent(ObjectTag.enemy );
        turretComponent.setProjectileName("redProjectile");
        turretComponent.setCountdown(0.2f);
        turretComponent.setProjectileSpread(0.6f);

        enemy.addComponent(turretComponent);

        //SPRITE COMPONENT
        enemy.addComponent(new SpriteComponent("enemy_airship_light.png"));

        //ENEMY COMPONENT
        EnemyComponent enemyComponent = new EnemyComponent(60);
        enemyComponent.setEnemyEnginePower(50);
        enemyAI ai = new enemyAI(enemyComponent,gameContainer );
        ai.setObjective( objective);
        ai.setAttckDistance(400);
        ai.setAttackObjectiveRange(600);
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
