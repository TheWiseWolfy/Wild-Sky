package com.apetrei.engine;

import com.apetrei.engine.components.Component;

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
        newComponent.setParent(this);
        newComponent.componentInit();
        components.add(newComponent);
    }

    //Update fuction

    public boolean isActive() {
        return active;
    }

    public Component getComponent(Class desiredClass){

        //Dupa multa experiemntatie, am ajuns la forma finala a acestei fuctii, in care:
        // -Daca ceri un obiect, si acesta este prezent, iti va fi returnat.
        // -Daca ceri un obiect, si un mostenitor al ecestuia este prezent, atunci iti va fi returnat acesta

        for(Component a : components){
            if(desiredClass.isAssignableFrom(a.getClass())){
                return a;
            }
        }
        return  null;
        //TODO Write Exception
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
