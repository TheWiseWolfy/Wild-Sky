package com.apetrei.engine.objects.components;

import com.apetrei.engine.objects.GameObject;
import com.apetrei.engine.objects.ObjectTag;
import com.apetrei.engine.physics.primitives.colliders.ConvexCollider;
import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.misc.Vector2;
import com.apetrei.misc.exceptions.ComponentMissingException;

import java.util.ArrayList;
import java.util.List;

public class TurretComponent extends Component{

    Rigidbody2D rigidbody = null;

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
        GameObject projectile = new GameObject(    this.getParent().getGameContainer() );

        Vector2 tranjectory =  new Vector2(rigidbody.getPosition()).sub(target).normalized().mul(-500f);
        Vector2 tranjectory2 =  new Vector2(rigidbody.getPosition()).sub(target).normalized().mul(-100);

        Rigidbody2D rigid = new Rigidbody2D( new Vector2(rigidbody.getPosition()).add(tranjectory2) ,tranjectory,0.1f);
        rigid.setRotation( - (float)Math.atan2(tranjectory.x, tranjectory.y) );
        projectile.addComponent(rigid);

        List<Vector2> waka2 = new ArrayList<Vector2>();

        waka2.add(new Vector2(-10, 10));
        waka2.add(new Vector2(10, 10));
        waka2.add(new Vector2(10, -10));
        waka2.add(new Vector2(-10, -10));

        ConvexPolygon2D wa2 = new ConvexPolygon2D(waka2);

        Collider2D colider4 = new ConvexCollider(true,wa2,(Collider2D collider) -> {

                System.out.print( "|");

               if( collider.parent.hasTag( ObjectTag.hasHealth) ){
                   try {
                       HealthInterface enemy =  (HealthInterface) collider.parent.getComponent( EnemyComponent.class );
                       enemy.substactHealt(10);
                   } catch (ComponentMissingException e) {
                       e.printStackTrace();
                   }
               }
            projectile.kill();
        } );

        projectile.addComponent(colider4);
        projectile.addComponent(new ProjectileComponent(0.5f) );
        projectile.addComponent(new SpriteComponent("Projectile.png")  );

        this.getParent().getGameContainer().getObjectManager().addGameObject(projectile);

    }

    @Override
    public void componentUpdate(double fT) {

    }

    @Override
    public void componentRender() {

    }
}
