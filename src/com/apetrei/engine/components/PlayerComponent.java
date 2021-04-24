package com.apetrei.engine.components;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.engine.GameContainer;
import com.apetrei.engine.input.InputType;
import com.apetrei.engine.physics.rigidbody.Rigidbody2D;
import com.apetrei.engine.renderer.Camera;
import com.apetrei.engine.renderer.Renderer;
import com.apetrei.misc.Line;
import com.apetrei.misc.Vector2;

import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class PlayerComponent extends Component  {
    private Rigidbody2D rigidbody;
    private TurretComponent turretComponent;

    private  Vector2 temptemp = new Vector2();
    public PlayerComponent(){
        super();
    }

    //Variabile gameplay
    private int engineLevel = 0;

    @Override
    public void componentInit() {
        try {
            rigidbody = (Rigidbody2D) parent.getComponent(Rigidbody2D.class);
            turretComponent = (TurretComponent) parent.getComponent( TurretComponent.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void componentUpdate(double fT) {

        GameContainer gameContainer =  GameContainer.getInstance();
        //Deplasare fata spate
        Vector2 forceToBeAplied = new Vector2( );



        if(gameContainer.getInput().isKey( KeyEvent.VK_W , InputType.DOWN)) {
            engineLevel = engineLevel < 3 ? ++engineLevel : engineLevel;
            System.out.println(engineLevel );
        }
        if(gameContainer.getInput().isKey( KeyEvent.VK_S , InputType.DOWN)) {
            engineLevel = engineLevel > -1 ? --engineLevel : engineLevel;
            System.out.println(engineLevel );
        }

        switch(engineLevel) {
            case 0:
                break;
            case 1:
                forceToBeAplied.add( rigidbody.getForward().mul( ConfigHandler.getEnginePower() *  0.2f ));
                break;
            case 2:
                forceToBeAplied.add( rigidbody.getForward().mul( ConfigHandler.getEnginePower() *  0.5f ));
                break;
            case 3:
                forceToBeAplied.add( rigidbody.getForward().mul( ConfigHandler.getEnginePower() ));
                break;
            case -1:
                forceToBeAplied.add( rigidbody.getForward().mul( ConfigHandler.getEnginePower() * - 0.2f ));
            default:
                // code block
        }

        rigidbody.addForce( forceToBeAplied );

        //Angular movement
        if(gameContainer.getInput().isKey(KeyEvent.VK_D, InputType.CONTINUOUS) ) {
            rigidbody.addAngularForce( ConfigHandler.getManeuverability());
        }
        if(gameContainer.getInput().isKey(KeyEvent.VK_A ,InputType.CONTINUOUS)) {
            rigidbody.addAngularForce( -ConfigHandler.getManeuverability());
        }

        temptemp =  gameContainer.getRenderer().getCamera().getMousePozInWorld( gameContainer.getInput().getMouseX() ,gameContainer.getInput().getMouseY() );

        if(gameContainer.getInput().isKey( KeyEvent.VK_SPACE , InputType.DOWN)) {
            turretComponent.fireProjectile( temptemp );
        }

        //Camera
        gameContainer.getRenderer().placeCameraAt( rigidbody.position);
    }


    @Override
    public void componentRender() {

        //Cod Debug
        Vector2 forward = new Vector2( rigidbody.getForward() );
        Vector2 result = new Vector2( rigidbody.getPosition() ).add( forward.mul(100f ) );

        GameContainer.getInstance().getRenderer().drawLine( new Line( new Vector2( rigidbody.position),new Vector2(result ) ));
        GameContainer.getInstance().getRenderer().drawLine( new Line( new Vector2( rigidbody.position),temptemp ));

    }

    //__________________________GETTERS___________________

    public int getEngineLevel() {
        return engineLevel;
    }

}
