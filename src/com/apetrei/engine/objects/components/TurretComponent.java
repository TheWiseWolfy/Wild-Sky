package com.apetrei.engine.objects.components;

import com.apetrei.engine.objects.GameObject;
import com.apetrei.engine.objects.ObjectTag;
import com.apetrei.engine.physics.primitives.colliders.ConvexCollider;
import com.apetrei.engine.sound.SoundManager;
import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.engine.physics.ShapeProvider;
import com.apetrei.misc.Vector2;
import com.apetrei.misc.exceptions.ComponentMissingException;

import java.util.Random;

public class TurretComponent extends Component{

    Rigidbody2D rigidbody = null;

    float colddown = 0.1f;
    float lastFiredTime;
    float timePassed = 0;
    float spawnDistance = 50f;
    float projectileSpread = 0.4f;
    float projectileSpeed = 600f;
    float projectileLifespawn = 2f;

    ObjectTag mask;

    public TurretComponent(ObjectTag mask){
        this.mask = mask;
    }

    @Override
    public void componentInit() {
        try {
            rigidbody = (Rigidbody2D) parent.getComponent(Rigidbody2D.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void fireProjectile( Vector2 target){


        if(lastFiredTime + colddown  < timePassed  ) {
            lastFiredTime = timePassed;
            SoundManager.getInstance().playSound("gun.wav");

            //Calculam traiectoria pentru proiectil
            Vector2 trajectory = new Vector2(rigidbody.getPosition()).sub(target).normalized();

            //Calculate random spread of projectile
            Random rand = new Random();
            float f = (float) ((  rand.nextFloat() * projectileSpread )  - projectileSpread/2);

            float trajectoryModifiedX = (float)  (  trajectory.x  * Math.cos(f) + trajectory.y * -Math.sin(f) );
            float trajectoryModifiedY = (float) -(  trajectory.y * -Math.cos(f) +  trajectory.x  * Math.sin(f) );

            Vector2 trajectoryModified = new Vector2( trajectoryModifiedX,trajectoryModifiedY ).mul(-projectileSpeed);

            Vector2 projectileSpawnPoint =  new Vector2(rigidbody.getPosition()).add(trajectoryModified.normalized().mul(spawnDistance));

            //Create projectile
            GameObject projectile = new GameObject(this.getParent().getGameContainer());

           //RIGIDBODY
            Rigidbody2D rigid = new Rigidbody2D(projectileSpawnPoint, trajectoryModified, 0.1f);
            rigid.setRotation(-(float) Math.atan2(trajectoryModified.x, trajectoryModified.y));
            projectile.addComponent(rigid);

            //COLLIDER
            ConvexPolygon2D polygon = new ConvexPolygon2D(ShapeProvider.getProjectileCollider());
            Collider2D projectileCollider = new ConvexCollider(true, polygon, (Collider2D collider) -> {
                if ( hitCondition(collider) ) {
                    if (collider.parent.hasComponent(HealthInterface.class)) {
                        try {
                            HealthInterface enemy = (HealthInterface) collider.parent.getComponent(HealthInterface.class);
                            enemy.substactHealth(10);
                        } catch (ComponentMissingException e) {
                            e.printStackTrace();
                        }
                    }
                    projectile.kill();
                }
            });
            projectile.addComponent(projectileCollider);

            //OTHER
            projectile.addComponent(new ProjectileComponent(projectileLifespawn));
            projectile.addComponent(new SpriteComponent("Projectile.png"));

            this.getParent().getGameContainer().getObjectManager().addGameObject(projectile);
        }
    }


    private boolean hitCondition(Collider2D collider){
        boolean value =  collider.parent != this.parent;
        value = value && !collider.parent.hasComponent(ProjectileComponent.class);
        value = value && !collider.parent.hasTag( mask);
        return value ;
    }

    public  void onProjectileColision( Collider2D collider2D){

    }

    @Override
    public void componentUpdate(double fT) {
        timePassed +=fT;
    }

    @Override
    public void componentRender() {

    }
}
