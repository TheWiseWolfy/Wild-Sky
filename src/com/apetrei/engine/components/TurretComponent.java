package com.apetrei.engine.components;

import com.apetrei.engine.GameObject;
import com.apetrei.engine.physics.primitives.colliders.ConvexCollider;
import com.apetrei.engine.physics.rigidbody.Rigidbody2D;
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
        GameObject projectile = new GameObject(getParent().getGameContainer()) ;
        projectile.addComponent( new Rigidbody2D() );

        projectile.addComponent(new Rigidbody2D( new Vector2(rigidbody.getPosition()) ,new Vector2(rigidbody.getPosition()).sub(target).mul(-1) ,5));
        projectile.addComponent(new SpriteComponent("C:\\Users\\Lucian\\Desktop\\Wild-Sky\\Wild-Sky\\src\\com\\resources\\FB004.png")  );

        getParent().getGameContainer().getObjectManager().addGameObject(projectile);

    }

    @Override
    public void componentUpdate(double fT) {

    }

    @Override
    public void componentRender() {

    }
}
