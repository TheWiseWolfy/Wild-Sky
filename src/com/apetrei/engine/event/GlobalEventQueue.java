package com.apetrei.engine.event;

import java.util.LinkedList;
import java.util.Queue;

public class GlobalEventQueue {

    private Queue<GlobalEvent> eventQueue = new LinkedList<>();

    public void declareEvent(GlobalEvent event ){
        eventQueue.add( event);
    }

    public GlobalEvent checkCurrentEvent(){
        if( eventQueue.peek() != null ) {
            return eventQueue.peek();
        }else return GlobalEvent.NULL;
    }

    public void nextEvent(){
        eventQueue.poll();
    }
}
