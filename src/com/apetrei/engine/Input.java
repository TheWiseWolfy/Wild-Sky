package com.apetrei.engine;
import java.awt.event.*;


public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    private GameContainer gc;

    private final int NUM_KEYS = 256; //De unde sa stiu eu cate butoane are utilizatorul
    private boolean[] keys = new boolean[NUM_KEYS];
    private boolean[] keysPast = new boolean[NUM_KEYS];

    private final int NUM_MOUSE_KEYS = 5; //De unde sa stiu eu cate butoane are mouseul
    private boolean[] mouseKeys = new boolean[NUM_MOUSE_KEYS];
    private boolean[] mouseKeysPast = new boolean[NUM_MOUSE_KEYS];

    private  int mouseX, mouseY;
    private int scroll;

//Public:
    public Input(GameContainer _gc) {
        gc = _gc;

        mouseX = 0;
        mouseY = 0;
        scroll = 0;

        gc.getWindow().getCanvas().addKeyListener(this);
        gc.getWindow().getCanvas().addMouseListener(this);
        gc.getWindow().getCanvas().addMouseMotionListener(this);
        gc.getWindow().getCanvas().addMouseWheelListener(this);
    }
    public  void  update(){

        for(int i = 0; i < NUM_KEYS; i++){
            keysPast[i] = keys[i];
        }
        for(int i = 0; i < NUM_MOUSE_KEYS; i++){
            mouseKeysPast[i] = mouseKeys[i];
        }
    }

    //Gestionam daca un buton e apasat, ect
    public boolean isKeyPressed(int keyCode){
        return keys[keyCode];
    }

    public boolean isKeyUp(int keyCode){
        return !keys[keyCode] && keysPast[keyCode];
    }

    public boolean isKeyDown(int keyCode){
        return keys[keyCode] && !keysPast[keyCode];
    }

    //Gestionam butoanele de pe mouse
    public boolean isMouseKeyPressed(int keyCode){
        return mouseKeys[keyCode];
    }

    public boolean isMouseKeyUp(int keyCode){
        return !mouseKeys[keyCode] && mouseKeysPast[keyCode];
    }

    public boolean isMouseKeyDown(int keyCode){
        return mouseKeys[keyCode] && !mouseKeysPast[keyCode];
    }

    //____________________________________________EVENTS________________________________________________
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[ e.getKeyCode() ] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[ e.getKeyCode() ] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseKeys[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseKeys[e.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = (int)(e.getX() / gc.getScale());
        mouseY  = (int)(e.getY() / gc.getScale());

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = (int)(e.getX() / gc.getScale() );
        mouseY = (int)(e.getY() / gc.getScale() );
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll = e.getWheelRotation();
    }

    //_______________________________________________________GETTER_______________
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



