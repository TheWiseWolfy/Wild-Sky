package com.apetrei.engine;

import java.util.ArrayList;

public class GameObject {

    //protected LinkedList<GameObject> children;
    protected ArrayList<Component> components;

    public boolean active = false;
    private String uniqueTag;

    public GameObject(){
        active = true;
        components = new ArrayList<Component>();
    }

    public void addComponent(Component newComponent ){
        components.add(newComponent);
    }

    //Update fuction

    public boolean isActive() {
        return active;
    }


    public Component getComponent(String componentName){

        for(Component a : components){
            if(a.getClass().getTypeName() == componentName){
                return a;
            }
        }
        return  null;
    }

    public  void update(GameContainer gameContainer){
        for( Component component : components){
            component.componentUpdate(gameContainer);
        }
    }
    public  void render(GameContainer gameContainer){
        for( Component component : components){
            component.componentRender(gameContainer);
        }
    }
}
