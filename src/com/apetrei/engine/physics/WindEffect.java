package com.apetrei.engine.physics;

import com.apetrei.engine.objects.GameObject;
import com.apetrei.engine.objects.components.WindInterface;
import com.apetrei.misc.ExtraMath;
import com.apetrei.misc.Vector2;
import com.apetrei.misc.exceptions.ComponentMissingException;
import com.apetrei.misc.observer.ObjectManagerObserver;
import com.apetrei.providers.GameContainer;

import java.util.ArrayList;
import java.util.List;

public class WindEffect implements ObjectManagerObserver {
    GameContainer gameContainer;

    private List<WindInterface> objects = new ArrayList<WindInterface>();

    Vector2 windDirection = new Vector2(1,4);
    float windForce = 30;

    WindEffect(GameContainer gameContainer){
        this.gameContainer = gameContainer;
    }

    public void update(){
        if( isThereWind() ) {
            for (var object : objects) {
                windDirection = windDirection.normalized();
                float forceModifier = Math.abs(object.getDragDirection().dot(windDirection.normalized()));

                forceModifier = Math.max(0.3f, forceModifier);
                forceModifier = Math.min(3, forceModifier);

                object.getRigitdbody().addForce(new Vector2(windDirection).mul(forceModifier * windForce));
            }
        }
    }

    public float getWindAngle(){
        return  (float) Math.atan2(windDirection.x, -windDirection.y);
    }

    public boolean isThereWind(){
       return !ExtraMath.equal(windForce,0);
    }

    private void addWindObject(WindInterface rigidbodies) {
        this.objects.add(rigidbodies);
    }

    private void removeWindObject(WindInterface rigidbodies) {
        this.objects.remove(rigidbodies);
    }

    public void setWind( Vector2 windDirection, float windForce){
        this.windDirection = windDirection;
        this.windForce = windForce;
    }

    //___________________________________________OBSERVER______________________________

    public void changeWindDirection(Vector2 newWindDirection){

        windDirection = newWindDirection.normalized();
    }


    @Override
    public void newObjectUpdate(GameObject created) {
        if( created.hasComponent(WindInterface.class) ) {
            try {
                addWindObject( (WindInterface) created.getComponent( WindInterface.class) );
            } catch (ComponentMissingException e) {
                System.err.println( "One with object with rigidbody has not been added to the wind system");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void objectDeletedUpdate(GameObject created) {
        if( created.hasComponent(WindInterface.class)){
            try {
                removeWindObject( (WindInterface) created.getComponent( WindInterface.class));
            } catch (ComponentMissingException e) {
                System.err.println( "One object has not been coretly removed from the wind list");
                e.printStackTrace();
            }
        }
    }

    //________________________________________GETTER_________________________________

    public Vector2 getWindDirection() {
        return windDirection.normalized();
    }
    public float getWindForce() {
        return windForce;
    }
}
