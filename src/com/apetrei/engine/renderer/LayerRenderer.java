package com.apetrei.engine.renderer;

import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.misc.Line;
import com.apetrei.misc.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class LayerRenderer {

    Graphics graphics;
    Camera camera;

    //private int pixelsW, pixelsH;
    //private int[] pixels;
    LayerRenderer(BufferedImage layer, Camera camera){
        this.graphics = layer.getGraphics();
        this.camera = camera;
    }

    //_____________________________________HERE WE DRAW____________________________
    //Fuction using the graphics class

    public void drawLine(Line line){
        graphics.setColor(Color.BLACK);

        int Ax = (int)( line.getA().x + camera.getCameraPosition().x );
        int Ay = (int)( line.getA().y + camera.getCameraPosition().y );
        int Bx = (int)( line.getB().x  + camera.getCameraPosition().x );
        int By = (int)( line.getB().y  + camera.getCameraPosition().y ) ;

        graphics.drawLine( Ax, Ay , Bx, By);
    }

    public void drawLine(Vector2 a, Vector2 b){
        graphics.setColor(Color.BLACK);

        a = camera.vector2CameraSpace(a);
        b = camera.vector2CameraSpace(b);

        graphics.drawLine((int) a.x, (int)a.y,(int)b.x  ,(int) b.y ) ;
    }

    //RECTANGLE
    public void drawRectangle(int x, int y, int wight, int height){
        graphics.setColor(Color.BLACK);
        graphics.drawRect(x,y ,wight,height);
    }

    public void drawRectangle(Vector2 min, Vector2 max, Color color){
        graphics.setColor(color);

        int wight =(int) (max.x - min.x);
        int height =(int) (max.y - min.y);

        graphics.fillRect((int)min.x,(int) min.y ,wight,height);
    }

    //ConvexPolygon2D

    public void drawPoligon(ConvexPolygon2D poly){
        graphics.setColor(Color.BLACK);

        List<Vector2> vertices = poly.getVertices();

        for (int i = 0; i < vertices.size() ; ++i) {
            drawLine(vertices.get(i), vertices.get( (i + 1) % vertices.size()) );
        }
    }
    //STATIC SPRITES

    public void drawStaticSprite(Vector2 poz,float scale, BufferedImage img){

        int possitionX = (int) (poz.x - ( img.getWidth() * 0.5f  * scale) ) ;
        int possitionY = (int) (poz.y - ( img.getHeight() * 0.5f * scale) ) ;

        int sizeX =(int) (img.getWidth() * scale ) ;
        int sizeY =(int) (img.getHeight() * scale ) ;

        graphics.drawImage(img,possitionX,possitionY ,sizeX ,sizeY,null);

    }



    //SPRITES
    public void drawSprite(Vector2 poz,float scale, BufferedImage img, float scrollfactor){

        Vector2 ajustedPoz =  camera.vector2CameraSpace( poz, scrollfactor);

        int possitionX = (int) (ajustedPoz.x  -  img.getWidth() * 0.5f  * scale ) ;
        int possitionY = (int) (ajustedPoz.y  -  img.getHeight() * 0.5f * scale ) ;

        int sizeX =(int) (img.getWidth() * scale ) ;
        int sizeY =(int) (img.getHeight() * scale ) ;

        graphics.drawImage(img,possitionX,possitionY ,sizeX ,sizeY,null);
    }

    public void drawSprite(Vector2 poz,float scale, BufferedImage img){

        Vector2 ajustedPoz =  camera.vector2CameraSpace( poz);

        int possitionX = (int) (ajustedPoz.x  -  img.getWidth() * 0.5f  * scale ) ;
        int possitionY = (int) (ajustedPoz.y  -  img.getHeight() * 0.5f * scale ) ;

        int sizeX =(int) (img.getWidth() * scale ) ;
        int sizeY =(int) (img.getHeight() * scale ) ;

        graphics.drawImage(img,possitionX,possitionY ,sizeX ,sizeY,null);
    }

    public void drawSprite( Vector2 poz,float scale,float rotation, BufferedImage img){

        img = rotate(img,rotation, (float)Math.PI /2);

        Vector2 ajustedPoz =  camera.vector2CameraSpace( poz);

        int possitionX = (int) (ajustedPoz.x  -  img.getWidth() * 0.5f  * scale ) ;
        int possitionY = (int) (ajustedPoz.y  -  img.getHeight() * 0.5f * scale ) ;

        int sizeX =(int) (img.getWidth() * scale ) ;
        int sizeY =(int) (img.getHeight() * scale ) ;

        graphics.drawImage(img ,possitionX,possitionY ,sizeX ,sizeY,null);
    }

    public static BufferedImage rotate(BufferedImage imgOld, float radians, float offset){                                                 //parameter same as method above

        BufferedImage imgNew = new BufferedImage(imgOld.getWidth(), imgOld.getHeight(), imgOld.getType());              //create new buffered image
        Graphics2D g = (Graphics2D) imgNew.getGraphics();                                                               //create new graphics
        g.rotate(radians + offset, imgOld.getWidth()/2, imgOld.getHeight()/2);                                    //configure rotation
        g.drawImage(imgOld, 0, 0, null);                                                                                //draw rotated image
        return imgNew;                                                                                                 //return rotated image
    }


    //________________________SETTER_____________________________________

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

}
