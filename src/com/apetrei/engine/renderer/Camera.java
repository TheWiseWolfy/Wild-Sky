package com.apetrei.engine.renderer;

import com.apetrei.engine.ConfigHandler;
import com.apetrei.misc.Vector2;

public class Camera {

    private Vector2 cameraPosition = new Vector2();

    float   limitSouth = 800;
    float   limitNorth = 800;
    float   limitWest = 800;
    float   limitEast = 800;

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

    public Vector2 coordinates2CameraSpace(int x, int y){
        Vector2 actualPoz = new Vector2( x, y);
        actualPoz.sub(cameraPosition);
        return  actualPoz;
    }

    //_____________________________SETTERS______________

    public void setCameraPosition(Vector2 cameraPosition) {
        this.cameraPosition = cameraPosition;

        if( cameraPosition.x <  -limitEast){
            cameraPosition.x = -limitEast;
        }
        else if( cameraPosition.x > limitWest){
            cameraPosition.x = limitWest;
        }

        if( cameraPosition.y < -limitSouth){
            cameraPosition.y = -limitSouth;
        }
        else if( cameraPosition.y > limitNorth){
            cameraPosition.y = limitNorth;
        }
    }

    public void setBounds( int sBound, int nBound, int wBound, int eBound){
        limitSouth =sBound;
        limitNorth = nBound;
        limitWest = wBound;
        limitEast = eBound;
    }

    //____________________________GETTERS__________________
    public Vector2 getCameraPosition() {
        return cameraPosition;
    }



}

