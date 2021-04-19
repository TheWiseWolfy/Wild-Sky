package com.apetrei.engine.renderer;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.engine.GameContainer;
import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.misc.Line;
import com.apetrei.misc.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.List;

public class Renderer {
    private GameContainer gc;
    private Graphics graphics;
    private BufferedImage image;

    private int pixelsW, pixelsH;
    private int[] pixels;

    //Aici stocam pozitia camerei
    private Camera camera;

    public Renderer(GameContainer _gameContainer){
        gc = _gameContainer;
        graphics = gc.getWindow().getGraphics();
        camera = new Camera();

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

    public void placeCameraAt( Vector2 newPoz){
        Vector2 newCameraPoz = new Vector2( newPoz ).mul( -1f ).add( new Vector2( ConfigHandler.getWidth()/2, ConfigHandler.getHeight()/2) );

        camera.setCameraPosition(newCameraPoz);
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

    public void drawLine(Vector2 a,  Vector2 b){
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

    public void drawRectangle(Vector2 min, Vector2 max){
        graphics.setColor(Color.BLACK);

        int wight =(int) (max.x - min.x);
        int height =(int) (max.y - min.y);

        graphics.drawRect((int)min.x,(int) min.y ,wight,height);
    }

    //ConvexPolygon2D

    public void drawPoligon(ConvexPolygon2D poly){
        graphics.setColor(Color.BLACK);

        List<Vector2> vertices = poly.getVertices();

        for (int i = 0; i < vertices.size() ; ++i) {
            drawLine(vertices.get(i), vertices.get( (i + 1) % vertices.size()) );
        }
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

    //___________________________________GETTER_________________

    public Camera getCamera() {
        return camera;
    }

}
