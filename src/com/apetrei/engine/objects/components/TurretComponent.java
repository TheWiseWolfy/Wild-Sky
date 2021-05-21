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

    float lastFiredTime;
    float timePassed = 0;

    //Proprieties
    float countdown = 0.3f;
    float spawnDistance = 50f;
    float projectileSpread = 0.4f;
    float projectileSpeed = 500f;
    float projectileLifespan = 3f;

    String projectileName = "orangeProjectile";
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

        if(lastFiredTime + countdown < timePassed  ) {
            lastFiredTime = timePassed;
            SoundManager.getInstance().playSound("fireball.wav",false);

            //Calculam traiectoria pentru proiectil
            Vector2 trajectory = new Vector2(rigidbody.getPosition()).sub(target).normalized();

            //Calculate random spread of projectile
            Random rand = new Random();
            float f = (float) ((  rand.nextFloat() * projectileSpread )  - projectileSpread/2);

            float trajectoryModifiedX = (float)  (  trajectory.x  * Math.cos(f) + trajectory.y * -Math.sin(f) );
            float trajectoryModifiedY = (float) -(  trajectory.y * -Math.cos(f) +  trajectory.x  * Math.sin(f) );

            Vector2 trajectoryModified = new Vector2( trajectoryModifiedX,trajectoryModifiedY ).mul(-projectileSpeed);
            Vector2 projectileSpawnPoint =  new Vector2(rigidbody.getPosition()).add(trajectoryModified.normalized().mul(spawnDistance));

            GameObject projectile = createProjectile(projectileSpawnPoint,trajectoryModified );
            this.getParent().getGameContainer().getObjectManager().addGameObject(projectile);
        }
    }

    //TODO: Some day move this out of this class
    private GameObject createProjectile(Vector2 projectileSpawnPoint, Vector2 trajectoryModified){
        //Create projectile
        GameObject projectile = new GameObject(this.getParent().getGameContainer());
        projectile.addTag(ObjectTag.projectile);

        //RIGIDBODY
        Rigidbody2D rigid = new Rigidbody2D(projectileSpawnPoint, trajectoryModified, 0.1f);
        rigid.setRotation(-(float) Math.atan2(trajectoryModified.x, trajectoryModified.y));
        projectile.addComponent(rigid);

        //COLLIDER
        ConvexPolygon2D polygon = new ConvexPolygon2D(ShapeProvider.getProjectileCollider());
        Collider2D projectileCollider = new ConvexCollider(true, polygon, (Collider2D collider) -> {
            //In cazul in care intram in coliziune cu orice
            if ( hitCondition(collider) ) {  //Daca obiectul nu e alt proiectil,nu e obiectul care il trage sau nu e imun la proectile
                if (collider.parent.hasComponent(HealthInterface.class)) {  //Daca inplmenenteaza interfata HealthInterface
                    try {
                        //Recupereazo si scade 10 unitati de viata
                        HealthInterface enemy = (HealthInterface) collider.parent.getComponent(HealthInterface.class);
                        enemy.substactHealth(10);
                    } catch (ComponentMissingException e) {
                        e.printStackTrace();
                    }
                }
                //Daca proiectilul loveste un obiect valid, o sa se autodistruga.
                projectile.kill();
            }
        });
        projectile.addComponent(projectileCollider);

        //OTHER
        projectile.addComponent(new TimerComponent(projectileLifespan));
        AnimatedSpriteComponent animator =new AnimatedSpriteComponent(projectileName,5, true);
        animator.setSpriteScale(0.7f);
        animator.playAnimation();
        projectile.addComponent(animator);
        return projectile;
    }

    private boolean hitCondition(Collider2D collider){
        boolean value =  collider.parent != this.parent;
        value = value && !collider.parent.hasTag(ObjectTag.projectile);
        value = value && !collider.parent.hasTag( mask);
        value = value && !collider.parent.hasTag( ObjectTag.ignoreProjectile);
        return value ;
    }

    @Override
    public void componentUpdate(double fT) {
        timePassed +=fT;
    }
    @Override
    public void componentRender() {
    }

    //______________________________SETTER_____________________

    public void setProjectileName(String projectileName) {
        this.projectileName = projectileName;
    }

    public void setCountdown(float countdown) {
        this.countdown = countdown;
    }
    public void setProjectileSpread(float projectileSpread) {
        this.projectileSpread = projectileSpread;
    }

    public void setProjectileSpeed(float projectileSpeed) {
        this.projectileSpeed = projectileSpeed;
    }

}
