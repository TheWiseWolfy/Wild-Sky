package com.apetrei.engine;

import java.util.ArrayList;

public class GameObject {

    //protected LinkedList<GameObject> children;
    protected ArrayList<Component> components;

    public boolean active = false;
    private String uniqueTag;
    public GameObject(){
        components = new ArrayList<Component>();
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

    public void addComponent(Component newComponent ){
        components.add(newComponent);
    }

    public boolean isActive() {
        return active;
    }
}
