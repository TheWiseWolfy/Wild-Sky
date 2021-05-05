package com.apetrei.engine.objects.components;

import com.apetrei.engine.objects.GameObject;
import com.apetrei.misc.Vector2;

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
        projectile.addComponent( new Rigidbody2D() );


        //target.mul(3);
        //target.mul(100);
       Vector2 tranjectory =  new Vector2(rigidbody.getPosition()).sub(target).normalized().mul(-500f);

        Rigidbody2D rigid = new Rigidbody2D( new Vector2(rigidbody.getPosition()) ,tranjectory,5);

        rigid.setRotation( - (float)Math.atan2(tranjectory.x, tranjectory.y) );
        projectile.addComponent(rigid);
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
