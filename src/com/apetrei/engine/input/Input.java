package com.apetrei.engine.input;
import com.apetrei.engine.ConfigHandler;
import com.apetrei.engine.GameContainer;
import com.google.inject.Key;

import java.awt.event.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;


public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    private GameContainer gameContainer;


    Set<Integer> pressedKeys = new TreeSet<Integer>();
    Set<Integer> pressedMouseKeys = new TreeSet<Integer>();

    private  int mouseX, mouseY;
    private int scroll;

    //Public:
    public Input(GameContainer _gameContainer) {
        gameContainer = _gameContainer;

        mouseX = 0;
        mouseY = 0;
        scroll = 0;

        gameContainer.getWindow().getCanvas().addKeyListener(this);
        gameContainer.getWindow().getCanvas().addMouseListener(this);
        gameContainer.getWindow().getCanvas().addMouseMotionListener(this);
        gameContainer.getWindow().getCanvas().addMouseWheelListener(this);
    }

    //The bread and butter of this class
    public Boolean isKey(int code, InputType type){

        switch (type){
            case CONTINUOUS:
               return pressedKeys.contains(code);

            case DOWN:
                if (getInput() != null ){
                    if( getInput().getEvent().getKeyCode() == code) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    //Gestionam butoanele de pe mouse
    public boolean isMouseKeyPressed(int keyCode){
        return pressedMouseKeys.contains(keyCode);
    }

    //_________________INPUT QUEUE___________________

    private Queue<InputEvent> inputEventQueue = new LinkedList<>();

    synchronized public void addInput( InputEvent inputEvent){
        inputEventQueue.add( inputEvent);
    }

    public void nextEvent(){
        inputEventQueue.poll();
    }

    //____________________________________________EVENTS________________________________________________
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if (pressedKeys.contains(code)) {
            return;
        }
        else {
            InputEvent event = new InputEvent(e , InputType.DOWN);
            gameContainer.getInput().addInput( event );
            pressedKeys.add( code);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //mouseKeys[e.getButton()] = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int code = e.getButton();

        if (pressedMouseKeys.contains(code)) {
            return;
        }
        else {
            //InputEvent event = new InputEvent(e , InputType.DOWN);
            //gameContainer.getInputQueue().addInput( event );
            pressedMouseKeys.add( code);
        }


    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressedKeys.remove(e.getButton());

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = (int)(e.getX() / ConfigHandler.getScale());
        mouseY  = (int)(e.getY() / ConfigHandler.getScale());

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = (int)(e.getX() / ConfigHandler.getScale() );
        mouseY = (int)(e.getY() /ConfigHandler.getScale() );
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll = e.getWheelRotation();
    }

    //_______________________________________________________GETTER_____________________________

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public InputEvent getInput( ){
        if ( inputEventQueue.isEmpty() )
            return null;
        return inputEventQueue.peek();
    }

    public int getScroll() {
        return scroll;
    }

}



