package com.apetrei.engine.objects.components;

import com.apetrei.engine.objects.GameObject;
import com.apetrei.engine.objects.ObjectTag;
import com.apetrei.engine.physics.primitives.colliders.ConvexCollider;
import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.misc.Vector2;
import com.apetrei.misc.exceptions.ComponentMissingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TurretComponent extends Component{

    Rigidbody2D rigidbody = null;

    float colddown = 0.1f;
    float lastFiredTime;
    float timePassed = 0;

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
            GameObject projectile = new GameObject(this.getParent().getGameContainer());

            Vector2 trajectory = new Vector2(rigidbody.getPosition()).sub(target).normalized();

           float x = trajectory.x;
            float y = trajectory.y;

            Random rand = new Random();
            float f = (float) ((  rand.nextFloat() % 0.4 )  -0.2);

            float tranjectoryModifiedX =(float) ( x  * Math.cos(f) + y * -Math.sin(f) );
            float tranjectoryModifiedY =(float)-(  y * -Math.cos(f) + x  * Math.sin(f) );

            Vector2 tranjectoryModified = new Vector2( tranjectoryModifiedX,tranjectoryModifiedY ).mul(-500f);

            Rigidbody2D rigid = new Rigidbody2D(new Vector2(rigidbody.getPosition()), tranjectoryModified, 0.1f);
            rigid.setRotation(-(float) Math.atan2(tranjectoryModified.x, tranjectoryModified.y));
            projectile.addComponent(rigid);

            List<Vector2> waka2 = new ArrayList<Vector2>();

            waka2.add(new Vector2(-10, 10));
            waka2.add(new Vector2(10, 10));
            waka2.add(new Vector2(10, -10));
            waka2.add(new Vector2(-10, -10));

            ConvexPolygon2D wa2 = new ConvexPolygon2D(waka2);
            Collider2D colider4 = new ConvexCollider(true, wa2, (Collider2D collider) -> {
                if (collider.parent != this.parent && !collider.parent.hasComponent(ProjectileComponent.class)) {
                    if (collider.parent.hasTag(ObjectTag.hasHealth)) {
                        try {
                            HealthInterface enemy = (HealthInterface) collider.parent.getComponent(HealthInterface.class);
                            enemy.substactHealt(10);
                        } catch (ComponentMissingException e) {
                            e.printStackTrace();
                        }
                    }
                    projectile.kill();
                }
            });
            projectile.addComponent(colider4);
            projectile.addComponent(new ProjectileComponent(1f));
            projectile.addComponent(new SpriteComponent("Projectile.png"));

            this.getParent().getGameContainer().getObjectManager().addGameObject(projectile);
        }
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
