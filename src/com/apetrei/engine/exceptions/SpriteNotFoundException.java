package com.apetrei.engine.exceptions;

public class SpriteNotFoundException extends Exception{

    String name;
    public SpriteNotFoundException(String name){
        this.name = name;
    }

    public String getMessage(){
        return "File \"" + name + "\" has not been found or is not loaded";
    }
}
