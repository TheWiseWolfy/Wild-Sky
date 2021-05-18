package com.apetrei.engine.renderer;

import com.apetrei.engine.providers.ConfigHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

//Clasa care gestioneaza fereastra care contine joculs
public class Window {

    private JFrame frame;
    private Canvas canvas;
    private BufferStrategy bufferStrategy;

    //Initializam clase si interfate inportante pentru randarea jocului
    public Window(){
      canvas = new Canvas();

      int realSizeX= (int)(ConfigHandler.getWidth()* ConfigHandler.getScale() );
      int realSizeY= (int)(ConfigHandler.getHeight()* ConfigHandler.getScale() );

      Dimension dimension = new Dimension(realSizeX,realSizeY);

       //Setari canvas
      canvas.setPreferredSize(dimension);
      canvas.setMaximumSize(dimension);
      canvas.setMinimumSize(dimension);

      //Setari pentru fereastra
      frame = new JFrame(ConfigHandler.getTitle());
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
    }

    public void close(){
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
    //________________________________GETTERS__________________________________________________

    public BufferStrategy getBufferStrategy() {
        return bufferStrategy;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getFrame() {
        return frame;
    }

}

