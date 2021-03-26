package com.apetrei.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

//Clasa care gestioneaza fereastra care contine joculs
public class Window {

    private JFrame frame;
    private Canvas canvas;
    private BufferStrategy bufferStrategy;
    private Graphics graphics;

    //Initializam clase si interfate inportante pentru randarea jocului
    public Window(GameContainer gc){
      canvas = new Canvas();

      Dimension s = new Dimension((int)(gc.getWidth()* gc.getScale()),(int)(gc.getHeight() * gc.getScale()));

       //Setari canvas
      canvas.setPreferredSize(s);
      canvas.setMaximumSize(s);
      canvas.setMinimumSize(s);

      //Setari pentru fereastra
      frame = new JFrame(gc.getTitle());
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLayout(new BorderLayout());
      frame.add(canvas,BorderLayout.CENTER);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setResizable(false);
      frame.setVisible(true);

      //Asocieri
      canvas.createBufferStrategy(2);
      bufferStrategy = canvas.getBufferStrategy();
      graphics = bufferStrategy.getDrawGraphics();

      //Aici avem imaginea pe care o vom desena pe ecran
    }


    //Chemat in GameContainer
    public void UpdateWindow(){
        bufferStrategy.show();
    }
    //________________________________GETTERS__________________________________________________

    /*
    public BufferedImage getImage() {
        return image;
    }
    */
    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getFrame() {
        return frame;
    }

    public Graphics getGraphics() {
        return graphics;
    }
}

