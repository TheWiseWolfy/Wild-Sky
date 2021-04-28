package com.apetrei.engine.renderer;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.misc.Vector2;

public class Camera {

     private Vector2 cameraPosition = new Vector2();

     public Vector2 getCameraPosition() {
        return cameraPosition;
    }

     public void setCameraPosition(Vector2 cameraPosition) {
        this.cameraPosition = cameraPosition;
    }

    public void placeCameraAt( Vector2 newPoz){
        Vector2 newCameraPoz = new Vector2( newPoz ).mul( -1f ).add( new Vector2( ConfigHandler.getWidth()/2, ConfigHandler.getHeight()/2) );

        this.setCameraPosition(newCameraPoz);
    }

    public Vector2 vector2CameraSpace( Vector2 vec ){
        return new Vector2(vec).add(cameraPosition);
    }

    public Vector2 vector2CameraSpace( Vector2 vec, float scrollFactor ){
        return new Vector2(vec).add(new Vector2(cameraPosition).mul( scrollFactor));
    }

    public Vector2 getMousePozInWorld(int mouseX, int mouseY){
         Vector2 actualPoz = new Vector2( mouseX, mouseY);
        actualPoz.sub(cameraPosition);
        return  actualPoz;
    }
}

