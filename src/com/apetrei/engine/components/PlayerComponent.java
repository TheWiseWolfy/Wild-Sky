package com.apetrei.engine.components;

import com.apetrei.engine.physics.rigidbody.Rigidbody2D;
import com.apetrei.misc.Line;
import com.apetrei.misc.Vector2;

import java.awt.event.KeyEvent;

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


        if(parent.getGameContainer().getInput().isKeyPressed(KeyEvent.VK_W)) {
            newVelocity = rigidbody.getForward().mul(2f);
          //  System.out.print(  newVelocity );
            rigidbody.setLinearVelocity(newVelocity.add(rigidbody.getLinearVelocity()));
        }
        if(parent.getGameContainer().getInput().isKeyPressed(KeyEvent.VK_S)) {
            Vector2 fecdfewjm = rigidbody.getForward();
            newVelocity = fecdfewjm.mul(-2f);
          //  System.out.print(  newVelocity.x );
            rigidbody.setLinearVelocity(newVelocity.add(rigidbody.getLinearVelocity()));
        }
        else {
            newVelocity.x = 0;
        }

        if(parent.getGameContainer().getInput().isKeyPressed(KeyEvent.VK_D ) ) {
            rigidbody.setRotation(rigidbody.getRotation() + 0.01f);

        }
        if(parent.getGameContainer().getInput().isKeyPressed(KeyEvent.VK_A)) {
            rigidbody.setRotation(rigidbody.getRotation() - 0.01f);

        }
        //Ration test

    }
    @Override
    public void componentRender() {

         Vector2 wac = rigidbody.getForward();
         Vector2 result = new Vector2(rigidbody.getPosition()).add(wac.mul(100f));
         parent.getGameContainer().getRenderer().drawLine( new Line( rigidbody.position,result ));
    }
}
