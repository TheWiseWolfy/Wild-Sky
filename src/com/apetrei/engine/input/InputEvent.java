package com.apetrei.engine.input;

import java.awt.event.KeyEvent;

public class InputEvent {

    KeyEvent event;
    InputType inputType;

    InputEvent( ){
        this.inputType = InputType.NULL;
    }

    InputEvent( KeyEvent event, InputType inputType){
        this.event = event;
        this.inputType = inputType;
    }

    public KeyEvent getEvent() {
        return event;
    }

    public InputType getInputType() {
        return inputType;
    }

}

