package com.apetrei.engine.input;

import com.apetrei.engine.providers.ConfigHandler;
import com.apetrei.engine.GameContainer;

import java.awt.event.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

/*!
 * Aici sunt gestionate toate inputurile primite de joc printr-o metoda hybrid
 */
public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    private GameContainer gameContainer;

    Set<Integer> pressedKeys = new TreeSet<Integer>();
    Set<Integer> pressedMouseKeys = new TreeSet<Integer>();

    private int mouseX, mouseY;
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
    public Boolean isKey(int code, InputType type) {

        if( type == InputType.CONTINUOUS ) {
            return pressedKeys.contains(code);
        }
        else if ( type == InputType.DOWN || type == InputType.UP ){

            if (getNextEventInQueue() != null &&  !getNextEventInQueue().isMouse() ) {
                KeyEvent event = (KeyEvent) getNextEventInQueue().getInputEvent();
                if (event.getKeyCode() == code && getNextEventInQueue().getInputType() == type) {
                    return true;
                }
            }
        }
        return false;
    }

    //Gestionam butoanele de pe mouse
    public boolean isMouseKey(int code, InputType type) {

            if( type == InputType.MOUSE_CONTINUOUS ) {
                return pressedMouseKeys.contains(code);
            }
            else if ( type == InputType.MOUSE_DOWN || type == InputType.MOUSE_UP ){
                if (getNextEventInQueue() != null && getNextEventInQueue().isMouse() ) {
                    MouseEvent event = (MouseEvent) getNextEventInQueue().getInputEvent();
                    if (event.getButton() == code && getNextEventInQueue().getInputType() == type) {
                        return true;
                    }
                }
             }
        return false;
    }

    //_________________INPUT QUEUE___________________

    private Queue<playerInputEvent> playerInputEventQueue = new LinkedList<>();

    private void addInput(playerInputEvent playerInputEvent) {
        playerInputEventQueue.add(playerInputEvent);
    }

    public void nextEvent() {
        playerInputEventQueue.poll();
    }

    private playerInputEvent getNextEventInQueue() {
        if (playerInputEventQueue.isEmpty())
            return null;
        return playerInputEventQueue.peek();
    }

    //____________________________________________EVENTS________________________________________________

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();
        if (pressedKeys.contains(code)) {
            return;
        } else {
            playerInputEvent event = new playerInputEvent(e, InputType.DOWN);
            addInput(event);
            pressedKeys.add(code);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        pressedKeys.remove(e.getKeyCode());
        playerInputEvent event = new playerInputEvent(e, InputType.UP);
        addInput(event);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int code = e.getButton();
        if (pressedMouseKeys.contains(code)) {
            return;
        } else {
            playerInputEvent event = new playerInputEvent(e, InputType.MOUSE_DOWN);
            addInput(event);
            pressedMouseKeys.add(code);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressedMouseKeys.remove(e.getButton());

        playerInputEvent event = new playerInputEvent(e, InputType.MOUSE_UP);
        addInput(event);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = (int) (e.getX() / ConfigHandler.getScale());
        mouseY = (int) (e.getY() / ConfigHandler.getScale());

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = (int) (e.getX() / ConfigHandler.getScale());
        mouseY = (int) (e.getY() / ConfigHandler.getScale());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll = e.getWheelRotation();
    }

    //_____________________________________________GETTER_________________________________

    public int getMouseX() {
        return mouseX;
    }
    public int getMouseY() {
        return mouseY;
    }
    public int getScroll() {
        return scroll;
    }
}



