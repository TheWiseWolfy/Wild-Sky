package com.apetrei.misc.exceptions;

public class ComponentMissingException extends Exception{

    String name;
    public ComponentMissingException(String name){
        this.name = name;
    }

    public String getMessage(){
        return "Component \"" + name + "\" or any of it's derivatives have not been found.";
    }
}
