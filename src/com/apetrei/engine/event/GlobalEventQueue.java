package com.apetrei.engine.event;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class GlobalEventQueue {

    private Queue<GlobalEvent> eventQueue = new LinkedList<>();
    private Set<GlobalEvent> hasHappened= new TreeSet<GlobalEvent>();

    public void declareEvent(GlobalEvent event ){
        eventQueue.add( event);
        hasHappened.add(event);
    }

    public GlobalEvent checkCurrentEvent(){
        if( eventQueue.peek() != null ) {
            return eventQueue.peek();
        }else return GlobalEvent.NULL;
    }

    public boolean didItHappen( GlobalEvent event ){
        return hasHappened.contains(event);
    }

    public void resetHistory(){
        hasHappened.clear();
    }

    public void nextEvent(){
        eventQueue.poll();
    }
}
