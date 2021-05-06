package com.apetrei.misc.exceptions;

public class GameObjectNotFoundException extends Exception{
        String name;
        public GameObjectNotFoundException(String name){
            this.name = name;
        }

        public String getMessage(){
            return "Game object with the tag \"" + name + "\" has not been found.";
        }
}
