package com.apetrei.engine.objects.components;

import com.apetrei.engine.event.GlobalEvent;
import com.apetrei.engine.objects.ObjectTag;
import com.apetrei.misc.observer.ObjectiveObserver;
import com.apetrei.misc.observer.PlayerObserver;

import java.util.ArrayList;
import java.util.List;

public class GameObjectiveComponent extends Component implements  HealthInterface{

    int maxObjectiveHealth;
    int objectiveHealth;

    private List<ObjectiveObserver> observers = new ArrayList<ObjectiveObserver>();

    public GameObjectiveComponent(int healt){
        this.maxObjectiveHealth = healt;
        objectiveHealth = maxObjectiveHealth;
    }


    @Override
    public void componentInit() {
        parent.addTag(ObjectTag.objective);
    }

    @Override
    public void componentUpdate(double fT) {
       // System.out.println( objectiveHealth);

        if( objectiveHealth <= 0){
            parent.kill();
            parent.getGameContainer().getGlobalEventQueue().declareEvent(GlobalEvent.OBJECTIVE_DESTROYED);
        }
    }
    @Override
    public void componentRender() {
    }

    public void addHealth(int value){
        if( objectiveHealth + value <= maxObjectiveHealth )  {
            objectiveHealth +=value;
        }else {
            objectiveHealth = maxObjectiveHealth;
        }
        notifyoObserver();
    }

    public void substactHealth(int value){
        if( objectiveHealth - value >= 0)  {
            objectiveHealth -=value;
        }else {
            objectiveHealth = 0;
        }
        notifyoObserver();

        parent.getGameContainer().getGlobalEventQueue().declareEvent(GlobalEvent.OBJECTIVE_DAMAGED);
    }

    //_________________________OBSERVER__________________

    final public void attach( ObjectiveObserver newObs){
        observers.add(newObs);
    }

    final public void dettach( ObjectiveObserver newObs){
        observers.remove(newObs);
    }

    final public void  notifyoObserver(){
        for ( var obs :observers ) {
            obs.objectiveUpdate( objectiveHealth );
        }
    }

}
