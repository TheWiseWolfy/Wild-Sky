package com.apetrei.engine.components;

import com.apetrei.engine.*;
import com.apetrei.engine.physics.primitives.colliders.Box2DCollider;
import com.apetrei.engine.physics.rigidbody.IntersectionDetector2D;
import com.apetrei.engine.physics.rigidbody.Rigidbody2D;
import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.misc.Line;
import com.apetrei.misc.Vector2;

import javax.swing.*;
import java.awt.*;

public class PlayerComponent extends Component {
    private Rigidbody2D rigidbody;
    public PlayerComponent(){
        super();
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

    @Override
    public void componentUpdate(double fT) {

        Vector2 newVelocity = new Vector2(rigidbody.getLinearVelocity() );


        if(parent.getGameContainer().getInput().isKeyPressed(65)) {
            newVelocity.y = -1;
            System.out.print(  newVelocity.x );
            rigidbody.setLinearVelocity(newVelocity);
        }
        if(parent.getGameContainer().getInput().isKeyPressed(68)) {
            newVelocity.y = 1;
            System.out.print(  newVelocity.x );
            rigidbody.setLinearVelocity(newVelocity);
        }

        if(parent.getGameContainer().getInput().isKeyPressed(87)) {
            newVelocity.x = -1;
            System.out.print(  newVelocity.x );
            rigidbody.setLinearVelocity(newVelocity);
         }
        if(parent.getGameContainer().getInput().isKeyPressed(83)) {
            newVelocity.x = 1 ;
            System.out.print(  newVelocity.x );
            rigidbody.setLinearVelocity(newVelocity);
        }else {
            newVelocity.x = 0;
        }

    }
    @Override
    public void componentRender() {

        Vector2[] waka = {
                new Vector2(0, 0),
                new Vector2(200, 200),
                new Vector2(300, 400),
                new Vector2(100, 600)
        };
        ConvexPolygon2D wa = new ConvexPolygon2D(waka);

         parent.getGameContainer().getRenderer().drawPoligon(wa);

        int x =-180;
        int y =0;
        Vector2[] waka2 = {
                new Vector2(370+x, 400+y),
                new Vector2(600+x, 500+y),
                new Vector2(620+x, 740+y),
                new Vector2(310+x, 720+y)
        };
        ConvexPolygon2D wa2 = new ConvexPolygon2D(waka2);

         parent.getGameContainer().getRenderer().drawPoligon(wa2);

        Vector2[] waka3 = IntersectionDetector2D.GetIntersectionOfPolygons(wa,wa2);
        ConvexPolygon2D wa3 = new ConvexPolygon2D( waka3 );

         parent.getGameContainer().getRenderer().drawPoligon(wa3, Color.RED);
    }
}
