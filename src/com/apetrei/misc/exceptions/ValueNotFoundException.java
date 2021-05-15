package com.apetrei.misc.exceptions;

public class ValueNotFoundException extends Throwable {

    String name;
    public ValueNotFoundException(String name){
        this.name = name;
    }

    public String getMessage(){
        return "File \"" + name + "\" has not been found in the current table.";
    }
}
