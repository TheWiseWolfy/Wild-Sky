package com.apetrei.engine.input;

import java.util.LinkedList;
import java.util.Queue;

public class InputQueue {
    Queue<InputEvent> inputEventQueue = new LinkedList<>();

    synchronized public void addInput( InputEvent inputEvent){
        inputEventQueue.add( inputEvent);
    }

    public InputEvent getInput( ){
        if ( inputEventQueue.isEmpty() )
            return null;
        return inputEventQueue.peek();
    }

     public void nextEvent(){
        inputEventQueue.poll();
    }
}
