package com.apetrei.engine.components;

import com.apetrei.engine.GameObject;
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

    public void fireProjectile(GameObject projectile, Vector2 target){

    }

    @Override
    public void componentUpdate(double fT) {

    }

    @Override
    public void componentRender() {

    }
}
