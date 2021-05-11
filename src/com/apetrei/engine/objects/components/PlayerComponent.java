package com.apetrei.engine.objects.components;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.engine.GameContainer;
import com.apetrei.engine.event.GlobalEvent;
import com.apetrei.engine.input.InputType;
import com.apetrei.engine.objects.ObjectTag;
import com.apetrei.misc.Line;
import com.apetrei.misc.Vector2;
import com.apetrei.misc.observer.PlayerObserver;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/*!
 * Componenta care pune jucatorul in controlui unei nave din joc, si actualizeaza pozitia camerei.
 */
public class PlayerComponent extends Component implements HealthInterface {

    private Rigidbody2D rigidbody;
    private TurretComponent turretComponent;
    private  Vector2 fireTarget = new Vector2();

    private List<PlayerObserver> observers = new ArrayList<PlayerObserver>();

    public PlayerComponent(){
        super();
    }

    //Variabile gameplay
    private int engineLevel = 0;
    private int playerHealt = ConfigHandler.getMaxPlayerHealt();

    @Override
    public void componentInit() {
        try {
            rigidbody = (Rigidbody2D) parent.getComponent(Rigidbody2D.class);
            turretComponent = (TurretComponent) parent.getComponent( TurretComponent.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        parent.addTag(ObjectTag.hasHealth);
        parent.addTag(ObjectTag.player);
    }

    @Override
    public void componentUpdate(double fT) {

        GameContainer gameContainer =   this.getParent().getGameContainer();

        //Deplasare fata spate
        Vector2 forceToBeAplied = new Vector2( );

        if(gameContainer.getInput().isKey( KeyEvent.VK_W , InputType.DOWN)) {
            engineLevel = engineLevel < 3 ? ++engineLevel : engineLevel;
            notifyoObserver();

            if(ConfigHandler.isDebugMode() ) System.out.println("Engine level: "+ engineLevel );
        }

        if(gameContainer.getInput().isKey( KeyEvent.VK_S , InputType.DOWN)) {
            engineLevel = engineLevel > -1 ? --engineLevel : engineLevel;
            notifyoObserver();

            if(ConfigHandler.isDebugMode() )  System.out.println("Engine level: "+ engineLevel );
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

        fireTarget =  gameContainer.getRenderer().getCamera().coordinates2CameraSpace( gameContainer.getInput().getMouseX() ,gameContainer.getInput().getMouseY() );

        if(gameContainer.getInput().isKey( KeyEvent.VK_SPACE , InputType.CONTINUOUS)) {
            turretComponent.fireProjectile(fireTarget);
        }

        //Camera
        gameContainer.getRenderer().getCamera().placeCameraAt( rigidbody.position);

    }

    @Override
    public void componentRender() {

        //Cod Debug
        if(ConfigHandler.isDebugMode() ) {
            Vector2 forward = new Vector2(rigidbody.getForward());
            Vector2 result = new Vector2(rigidbody.getPosition()).add(forward.mul(100f));

            this.getParent().getGameContainer().getRenderer().getLayerRenderer().drawLine(new Line(new Vector2(rigidbody.position), new Vector2(result)));
            this.getParent().getGameContainer().getRenderer().getLayerRenderer().drawLine(new Line(new Vector2(rigidbody.position), fireTarget));
        }
    }
    //_________________________OBSERVER__________________

    final public void attach( PlayerObserver newObs){

        observers.add(newObs);
    }

    final public void dettach( PlayerObserver newObs){
        observers.remove(newObs);
    }

    final public void  notifyoObserver(){
        for ( var obs :observers ) {
            obs.playerUpdate( engineLevel, playerHealt );
        }
    }

    //__________________________GETTERS___________________

    //__________________________SETTERS___________________

    public void addHealth(int value){
        if( playerHealt + value <= ConfigHandler.getMaxPlayerHealt() )  {
            playerHealt +=value;
        }else {
            playerHealt = ConfigHandler.getMaxPlayerHealt();
        }
         notifyoObserver();
    }

    public void substactHealth(int value){
        if( playerHealt - value >= 0)  {
           playerHealt -=value;
        }else {
            playerHealt = 0;

            parent.getGameContainer().getGlobalEventQueue().declareEvent(GlobalEvent.PLAYER_DESTROYED);
        }
        notifyoObserver();
    }

}
