package com.apetrei.engine.objects.components;

import com.apetrei.engine.providers.ConfigHandler;
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
public class PlayerComponent extends Component implements HealthInterface, WindInterface {

    private Rigidbody2D rigidbody;
    private TurretComponent turretComponent;
    private AnimatedSpriteComponent animatedSprite;
    private Vector2 fireTarget = new Vector2();

    private float enginePower = ConfigHandler.getEnginePower();
    private float maneuverability = ConfigHandler.getManeuverability();

    public PlayerComponent(){
        super();
    }

    //Variabile gameplay
    private int engineLevel = 0;
    private int playerHealt = ConfigHandler.getMaxPlayerHealt();

    private float currentSailsModifier = ConfigHandler.getWithouthSailsDrag();

    @Override
    public void componentInit() {
        try {
            rigidbody = (Rigidbody2D) parent.getComponent(Rigidbody2D.class);
            turretComponent = (TurretComponent) parent.getComponent( TurretComponent.class);
            animatedSprite = (AnimatedSpriteComponent) parent.getComponent(AnimatedSpriteComponent.class );
        }
        catch (Exception e){
            e.printStackTrace();
        }
        parent.addTag(ObjectTag.player);
    }

    @Override
    public void componentUpdate(double fT) {

        GameContainer gameContainer =   this.getParent().getGameContainer();

        //Deplasare fata spate
        Vector2 forceToBeAplied = new Vector2();

        if(gameContainer.getInput().isKey( KeyEvent.VK_W , InputType.DOWN)) {
            engineLevel = engineLevel < 3 ? ++engineLevel : engineLevel;

            if(ConfigHandler.isDebugMode() ) System.out.println("Player position: "+ rigidbody.position );
            if(ConfigHandler.isDebugMode() ) System.out.println("Engine level: "+ engineLevel );
        }

        if(gameContainer.getInput().isKey( KeyEvent.VK_S , InputType.DOWN)) {
            engineLevel = engineLevel > -1 ? --engineLevel : engineLevel;

            if(ConfigHandler.isDebugMode() )  System.out.println("Engine level: "+ engineLevel );
        }

        switch(engineLevel) {
            case 0:
                break;
            case 1:
                forceToBeAplied.add( rigidbody.getForward().mul(enginePower *  0.2f ));
                break;
            case 2:
                forceToBeAplied.add( rigidbody.getForward().mul( enginePower *  0.5f ));
                break;
            case 3:
                forceToBeAplied.add( rigidbody.getForward().mul(enginePower ));
                break;
            case -1:
                forceToBeAplied.add( rigidbody.getForward().mul( enginePower * - 0.2f ));
            default:
                // code block
        }
        rigidbody.addForce( forceToBeAplied );

        //Angular movement
        if(gameContainer.getInput().isKey(KeyEvent.VK_D, InputType.CONTINUOUS) ) {
            rigidbody.addAngularForce( maneuverability);
        }
        if(gameContainer.getInput().isKey(KeyEvent.VK_A ,InputType.CONTINUOUS)) {
            rigidbody.addAngularForce( -maneuverability);
        }

        //SHIFT FUCTIONALITY
        if( gameContainer.getInput().isKey(KeyEvent.VK_SHIFT ,InputType.DOWN ) ){
            animatedSprite.playAnimation();

            currentSailsModifier = ConfigHandler.getWithouthSailsDrag();
            enginePower = ConfigHandler.getEnginePowerWithouthSails();
            maneuverability = ConfigHandler.getManeuverabilityWithouthSails();

            if(ConfigHandler.isDebugMode() )  System.out.println("Current wind modifier:" + currentSailsModifier);
        }
        if( gameContainer.getInput().isKey(KeyEvent.VK_SHIFT ,InputType.UP ) ){
            animatedSprite.playAnimationBackwards();

            currentSailsModifier = ConfigHandler.getSailsDrag();
            enginePower = ConfigHandler.getEnginePower();
            maneuverability = ConfigHandler.getManeuverability();

            if(ConfigHandler.isDebugMode() )   System.out.println("Current wind modifier:" +currentSailsModifier);
        }

        //FIRE WEAPON
        fireTarget =  gameContainer.getRenderer().getCamera().mouseInGameSpace();
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

    //__________________________WIND_INTERFACE___________________
    @Override
    public Vector2 getDragDirection() {
        return new Vector2( rigidbody.getForward() ).mul(currentSailsModifier);
    }

    @Override
    public Rigidbody2D getRigitdbody() {
        return rigidbody;
    }

    //________________________GETTER___________________

    public int getEngineLevel() {
        return engineLevel;
    }

    //__________________________HEALT_INTERFACE___________________

    public void addHealth(int value){
        if( playerHealt + value <= ConfigHandler.getMaxPlayerHealt() )  {
            playerHealt +=value;
        }else {
            playerHealt = ConfigHandler.getMaxPlayerHealt();
        }
    }

    public void substactHealth(int value){
        if( playerHealt - value >= 0)  {
            playerHealt -=value;
        }else {
            playerHealt = 0;
            parent.getGameContainer().getGlobalEventQueue().declareEvent(GlobalEvent.PLAYER_DESTROYED);
        }
    }

    @Override
    public int getMaxHealth() {
        return ConfigHandler.getMaxPlayerHealt();
    }

    @Override
    public int getHealth() {
        return playerHealt;
    }
}
