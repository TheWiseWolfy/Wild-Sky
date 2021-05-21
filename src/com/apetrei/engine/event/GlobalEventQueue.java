package com.apetrei.engine.event;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

/*!
 * O clasa care tine evidenta lucrurilor care s-au intamplat in campanie pentru ca alte clase sa opereze pe aceasta informatie
 */
public class GlobalEventQueue {

    private Queue<GlobalEvent> eventQueue = new LinkedList<>();
    private Set<GlobalEvent> globalHasHappened = new TreeSet<GlobalEvent>();

    public void declareEvent(GlobalEvent event ){
        eventQueue.add( event);
        globalHasHappened.add(event);
    }
    public GlobalEvent checkCurrentEvent(){
        if( eventQueue.peek() != null ) {
            return eventQueue.peek();
        }else return GlobalEvent.NULL;
    }

    public void nextEvent(){
        eventQueue.poll();
    }
    public boolean didItHappen( GlobalEvent event ){
        return globalHasHappened.contains(event);
    }
    public void resetHistory(){
        globalHasHappened.clear();
    }
}
