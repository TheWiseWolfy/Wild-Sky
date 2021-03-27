package com.apetrei.engine;

import java.util.ArrayList;

public class GameObject {

    //protected LinkedList<GameObject> children;
    protected ArrayList<Component> components;

    protected GameContainer gameContainer;

    public boolean active = false;
    private String uniqueTag;

    public GameObject(GameContainer _gameContainer){
        gameContainer = _gameContainer;
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
            if(a.getClass().getSimpleName() == componentName){
                return a;
            }
        }
        return  null;
    }

    public  void update(double fT){
        for( Component component : components){
            component.componentUpdate(fT);
        }
    }
    public  void render( ){
        for( Component component : components){
            component.componentRender();
        }
    }

    //_______________________________GETTER________________

    public GameContainer getGameContainer() {
        return gameContainer;
    }

}
