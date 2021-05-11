package com.apetrei.engine.objects.components;

import com.apetrei.engine.ai.enemyAI;
import com.apetrei.engine.objects.GameObject;
import com.apetrei.engine.objects.ObjectTag;
import com.apetrei.misc.Vector2;
import com.apetrei.misc.exceptions.ComponentMissingException;

public class EnemyComponent extends Component implements HealthInterface{

    private Rigidbody2D rigidbody;
    private TurretComponent turretComponent;
    private enemyAI ai;

    GameObject objective;

    int maxHealt = 1000;
    int healt = maxHealt;

    public EnemyComponent(GameObject objective){
        super();
        this.objective = objective;
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

        parent.addTag(ObjectTag.hasHealth);
        parent.addTag(ObjectTag.enemy);
        ai = new enemyAI(this, this.getParent().getGameContainer() );
        ai.setObjective( objective);

    }

    //_________________________________COMPONENT__________________________
    @Override
    public void componentUpdate(double fT) {
        ai.updateAI();

        if( healt <= 0){
            this.parent.kill();
        }
    }

    @Override
    public void componentRender() {
    }

    //____________________________POSSIBLE COMMANDS_______________


    public void chaseTarger(GameObject gameObject){

        try {
            Rigidbody2D targetRigidbody = (Rigidbody2D) gameObject.getComponent(Rigidbody2D.class);
            goToPosition( targetRigidbody.getPosition() );

        } catch (ComponentMissingException e) {
            e.printStackTrace();
        }
    }

    public void goToPosition(Vector2 pos) {
        Vector2 direction = new Vector2(pos).sub(rigidbody.getPosition());
        direction = direction.normalized();
        rigidbody.rotation =  - (float)Math.atan2(direction.x, direction.y) +(float) Math.PI /2f;
        rigidbody.addForce(direction.mul(100f));
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


    //________________________HealthInterface________________________
    public void addHealth(int value){
        if( healt + value <= maxHealt)  {
            healt +=value;
        }else {
            healt = maxHealt;
        }
    }

    public void substactHealth(int value){
        if( healt - value >= 0)  {
            healt -=value;
        }else {
            healt = 0;
        }
    }
}
