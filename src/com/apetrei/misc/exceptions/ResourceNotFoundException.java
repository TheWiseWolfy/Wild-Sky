package com.apetrei.misc.exceptions;

public class ResourceNotFoundException extends Exception{

    String name;
    public ResourceNotFoundException(String name){
        this.name = name;
    }

    public String getMessage(){
        return "File \"" + name + "\" has not been found or is not loaded";
    }
}
