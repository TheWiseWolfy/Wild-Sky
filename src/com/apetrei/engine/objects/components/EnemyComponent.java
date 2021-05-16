package com.apetrei.engine.objects.components;

import com.apetrei.engine.ai.enemyAI;
import com.apetrei.engine.event.GlobalEvent;
import com.apetrei.engine.objects.GameObject;
import com.apetrei.engine.objects.ObjectTag;
import com.apetrei.misc.Vector2;
import com.apetrei.misc.exceptions.ComponentMissingException;

public class EnemyComponent extends Component implements HealthInterface{

    private Rigidbody2D rigidbody;
    private TurretComponent turretComponent;
    private enemyAI ai;

    int maxHealt = 100;
    int healt = maxHealt;

    float enemyEnginePower = 50;

    public EnemyComponent(int maxHealt){
        super();
        this.maxHealt = maxHealt;
        healt = maxHealt;
    }

    // ________________________Component______________________
    @Override
    public void componentInit() {
        try {
            rigidbody = (Rigidbody2D) parent.getComponent(Rigidbody2D.class);
            turretComponent = (TurretComponent) parent.getComponent( TurretComponent.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        parent.addTag(ObjectTag.enemy);
    }

    //_________________________________COMPONENT__________________________
    @Override
    public void componentUpdate(double fT) {
        if(ai != null) {
            ai.updateAI();
        }
        if( healt <= 0){
            this.parent.kill();
            parent.getGameContainer().getGlobalEventQueue().declareEvent( GlobalEvent.ENEMY_DESTROYED );
        }
    }

    @Override
    public void componentRender() {
    }

    //____________________________POSSIBLE COMMANDS_______________

    public void chaseTarget(GameObject gameObject){
        try {
            Rigidbody2D targetRigidbody = (Rigidbody2D) gameObject.getComponent(Rigidbody2D.class);
            goToPosition( targetRigidbody.getPosition() );

        } catch (ComponentMissingException e) {
            e.printStackTrace();
        }
    }

    public void runFromTarget(GameObject gameObject){

        try {
            Rigidbody2D targetRigidbody = (Rigidbody2D) gameObject.getComponent(Rigidbody2D.class);
            getFurtherFrom( targetRigidbody.getPosition() );

        } catch (ComponentMissingException e) {
            e.printStackTrace();
        }
    }

    public void goToPosition(Vector2 pos) {
        Vector2 direction = new Vector2(pos).sub(rigidbody.getPosition());
        direction = direction.normalized();
        rigidbody.rotation =  - (float)Math.atan2(direction.x, direction.y) +(float) Math.PI /2f;
        rigidbody.addForce(direction.mul(enemyEnginePower));
    }

    public void getFurtherFrom(Vector2 pos ){
        Vector2 direction = new Vector2(pos).sub(rigidbody.getPosition());
        direction = direction.normalized();
        rigidbody.rotation =  - (float)Math.atan2(direction.x, direction.y) + (float) Math.PI /2f;
        rigidbody.addForce(direction.mul(-enemyEnginePower));
    }

    public float distanceTo(GameObject gameObject) {

        try {
            Rigidbody2D targetRigidbody = (Rigidbody2D) gameObject.getComponent(Rigidbody2D.class);
            Vector2 direction = new Vector2(targetRigidbody.getPosition()).sub(rigidbody.getPosition());
            return direction.getMagnitue();

        } catch (ComponentMissingException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public float destroy(GameObject gameObject) {

        try {
            Rigidbody2D targetRigidbody = (Rigidbody2D) gameObject.getComponent(Rigidbody2D.class);
            turretComponent.fireProjectile(targetRigidbody.position );
        } catch (ComponentMissingException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public void mentainDistance(GameObject gameObject, int minDistance, int maxDistance) {
        if( distanceTo(gameObject) > minDistance){
            chaseTarget(gameObject);
        }else if( distanceTo(gameObject) < maxDistance){
            runFromTarget(gameObject);
        }

    }

    //________________________SETTER_______________________________

    public void setAi(enemyAI ai) {
        this.ai = ai;
    }

    public void setEnemyEnginePower(float enemyEnginePower) {
        this.enemyEnginePower = enemyEnginePower;
    }


    //________________________HealthInterface________________________
    @Override
    public void addHealth(int value){
        if( healt + value <= maxHealt)  {
            healt +=value;
        }else {
            healt = maxHealt;
        }
    }
    @Override
    public void substactHealth(int value){
        if( healt - value >= 0)  {
            healt -=value;
        }else {
            healt = 0;
        }
    }

    @Override
    public int getMaxHealth() {
        return maxHealt;
    }

    @Override
    public int getHealth() {
        return healt;
    }

}
