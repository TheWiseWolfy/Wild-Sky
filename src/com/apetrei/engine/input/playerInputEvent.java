package com.apetrei.engine.input;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/*!
 * O clasa menita sa stocheze diferite tipuri de evenimente care vin din partea utilizatorului
 */
public class playerInputEvent {

    private InputEvent event;
    private InputType inputType;
    private boolean isMouseEvent;

    playerInputEvent(InputEvent event, InputType inputType){
        this.event = event;
        this.inputType = inputType;

        if( inputType == InputType.MOUSE_CONTINUOUS || inputType == InputType.MOUSE_DOWN || inputType == InputType.MOUSE_UP) {
            isMouseEvent = true;
        }
        else {
            isMouseEvent = false;
        }
    }

    public InputEvent getInputEvent(){
        return event;
    }
    public InputType getInputType() {
        return inputType;
    }

    public boolean isMouse(){
        return isMouseEvent;
    }
}

