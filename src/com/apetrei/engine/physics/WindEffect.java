package com.apetrei.engine.physics;

import com.apetrei.engine.objects.GameObject;
import com.apetrei.engine.objects.components.Collider2D;
import com.apetrei.engine.objects.components.WindInterface;
import com.apetrei.misc.Vector2;
import com.apetrei.misc.exceptions.ComponentMissingException;
import com.apetrei.misc.observer.ObjectManagerObserver;

import java.util.ArrayList;
import java.util.List;

public class WindEffect implements ObjectManagerObserver {

    private List<WindInterface> objects = new ArrayList<WindInterface>();

    Vector2 windForce = new Vector2(-40,0);
    WindEffect(){
    }

    public void update(){
        for ( var object : objects) {

            float forceModifier = Math.abs( object.getDrag().dot(  windForce.normalized()) );

            forceModifier = Math.max(0,forceModifier);
            forceModifier = Math.min(3,forceModifier);

            object.getRigitdbody().addForce( new Vector2(windForce).mul(forceModifier));
        }
    }

    private void addWindObject(WindInterface rigidbodies) {
        this.objects.add(rigidbodies);
    }

    private void removeWindObject(WindInterface rigidbodies) {
        this.objects.remove(rigidbodies);
    }

    //___________________________________________OBSERVER______________________________

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
}
