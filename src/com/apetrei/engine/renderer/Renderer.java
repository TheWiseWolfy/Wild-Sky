package com.apetrei.engine.renderer;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.engine.GameContainer;
import com.apetrei.engine.physics.primitives.colliders.Box2DCollider;
import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.misc.ExtraMath;
import com.apetrei.misc.Line;
import com.apetrei.misc.Vector2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.util.List;

public class Renderer {
    private GameContainer gc;
    private Graphics graphics;
    private BufferedImage image;

    private int pixelsW, pixelsH;
    private int[] pixels;

    public Renderer(GameContainer _gameContainer){
        gc = _gameContainer;
        graphics = gc.getWindow().getGraphics();

        pixelsW = ConfigHandler.getWidth();
        pixelsH = ConfigHandler.getHeight();

        image = new BufferedImage(ConfigHandler.getWidth(), ConfigHandler.getHeight(),BufferedImage.TYPE_INT_ARGB);

    }

    public void Render(){

        int realSizeX= (int)(ConfigHandler.getWidth()* ConfigHandler.getScale() );
        int realSizeY= (int)(ConfigHandler.getHeight()* ConfigHandler.getScale() );

        //Sprite
         graphics.clearRect(0,0,realSizeX,realSizeY);

         pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

         gc.getObjectManager().renderObjects();


    }

    public void Display(){
        int realSizeX= (int)(ConfigHandler.getWidth() * ConfigHandler.getScale() );
        int realSizeY= (int)(ConfigHandler.getHeight() * ConfigHandler.getScale() );

        graphics.drawImage(image,0,0,realSizeX,realSizeY ,null);

        //Final render
        gc.getWindow().UpdateWindow();
    }

    //Fuction using the graphics class

    //LINE
    public void drawLine(int x1, int y1, int x2, int y2){
        graphics.setColor(Color.BLACK);
        graphics.drawLine(x1,y1 ,x2,y2);
    }

    public void drawLine(Line line){
        graphics.setColor(Color.BLACK);
        graphics.drawLine((int) line.getA().x,(int) line.getA().y ,(int) line.getB().x,(int) line.getB().y);
    }

    public void drawLine(Line line, Color col){
        graphics.setColor(col);
        graphics.drawLine((int) line.getA().x,(int) line.getA().y ,(int) line.getB().x,(int) line.getB().y);
    }

    public void drawLine(Vector2 a,  Vector2 b){
        graphics.setColor(Color.BLACK);
        graphics.drawLine((int)a.x,(int)a.y ,(int)b.x,(int)b.y);
    }
    public void drawLine(Vector2 a,  Vector2 b,Color col){
        graphics.setColor(col);
        graphics.drawLine((int)a.x,(int)a.y ,(int)b.x,(int)b.y);
    }

    public void drawLine(int x1, int y1, int x2, int y2,Color col){
        graphics.setColor(col);
        graphics.drawLine(x1,y1 ,x2,y2);
    }

    //RECTANGLE
    public void drawRectangle(int x, int y, int wight, int height){
        graphics.setColor(Color.BLACK);
        graphics.drawRect(x,y ,wight,height);
    }

    public void drawRectangle(Vector2 min, Vector2 max){
        graphics.setColor(Color.BLACK);

        int wight =(int) (max.x - min.x);
        int height =(int) (max.y - min.y);

        graphics.drawRect((int)min.x,(int) min.y ,wight,height);
    }

    public void drawRectangle(Vector2 min, Vector2 max,Color col){
        graphics.setColor(col);

        int wight =(int) (max.x - min.x);
        int height =(int) (max.y - min.y);

        graphics.drawRect((int)min.x,(int) min.y ,wight,height);
    }
    public void drawRectangle(int x, int y, int wight, int height, Color col){
        graphics.setColor(col);
        graphics.drawRect(x,y ,wight,height);
    }

    //ConvexPolygon2D

    public void drawPoligon(ConvexPolygon2D poly){
        graphics.setColor(Color.BLACK);

        List<Vector2> vertices = poly.getVertices();

        for (int i = 0; i < vertices.size() ; ++i) {
            drawLine(vertices.get(i), vertices.get( (i + 1) % vertices.size()) );
        }
        //graphics.drawRect(x,y ,wight,height,6);
    }

    public void drawPoligon(ConvexPolygon2D poly,Color col){
        List<Vector2> vertices = poly.getVertices();

        for (int i = 0; i < vertices.size() ; ++i) {
            drawLine(vertices.get(i), vertices.get ((i + 1) % vertices.size() ),col);
        }
    }

    //SPRITES
    public void drawSprite(float x, float y,float scale, BufferedImage img){

        /////////
        int possitionX =(int) (x  -  img.getWidth() * 0.5f  * scale  ) ;
        int possitionY= (int) (y  -  img.getHeight() * 0.5f * scale ) ;

        int sizeX =(int) (img.getWidth() * scale ) ;
        int sizeY =(int) (img.getHeight() * scale ) ;

        graphics.drawImage(img,possitionX,possitionY ,sizeX ,sizeY,null);
    }

    public void drawSprite(float x, float y,float scale,float rotation, BufferedImage img){

            img = rotate(img,rotation, (float)Math.PI /2);


            /////////
        int possitionX =(int) (x  -  img.getWidth() * 0.5f  * scale  ) ;
        int possitionY= (int) (y  -  img.getHeight() * 0.5f * scale ) ;

        int sizeX =(int) (img.getWidth() * scale ) ;
        int sizeY =(int) (img.getHeight() * scale ) ;

        graphics.drawImage(img,possitionX,possitionY ,sizeX ,sizeY,null);
    }


    //Fuctions operating on pixel array

    public void setPixel( int x, int y){
        pixels[y * pixelsW + x] = 0xFF000000;

    }

    public void clear(){
        for (int i = 0; i < pixels.length; i++){
            pixels[i] = 0x00000000;
        }
    }

    public static BufferedImage rotate(BufferedImage imgOld, float radians, float offset){                                                 //parameter same as method above

        BufferedImage imgNew = new BufferedImage(imgOld.getWidth(), imgOld.getHeight(), imgOld.getType());              //create new buffered image
        Graphics2D g = (Graphics2D) imgNew.getGraphics();                                                               //create new graphics
        g.rotate(radians + offset, imgOld.getWidth()/2, imgOld.getHeight()/2);                                    //configure rotation
        g.drawImage(imgOld, 0, 0, null);                                                                                //draw rotated image
        return imgNew;                                                                                                  //return rotated image
    }

}
