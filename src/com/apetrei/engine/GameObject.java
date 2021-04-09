package com.apetrei.engine;

import com.apetrei.engine.components.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//O clasa care incapsuleaza componente, care constau in informatie si logica care colaboreaza intre ele in mod modular.
public class GameObject {

    //protected LinkedList<GameObject> children;
    //protected ArrayList<Component> components;
    HashMap<String, Component> components;

    protected GameContainer gameContainer;

    public boolean active = false;
    private String uniqueTag;

    public GameObject(GameContainer _gameContainer){
        gameContainer = _gameContainer;
        active = true;
        //components = new ArrayList<Component>();
        components = new HashMap<String, Component>();
    }

    public void addComponent(Component newComponent ){
        newComponent.setParent(this);
        newComponent.componentInit();
        components.put(newComponent.getClass().getSimpleName() ,newComponent);
        //components.add(newComponent);
    }

    public boolean hasComponent(Component component){
        return  components.containsKey( component.getClass().getSimpleName() );
    }

    //Update fuctions

    public boolean isActive() {
        return active;
    }

    //Fuctii de actualizare

    public  void update(double fT){

        for (Map.Entry<String, Component> componentEntry : components.entrySet()) {
            componentEntry.getValue().componentUpdate(fT);
        }

        /*
        for( Component component : components){
            component.componentUpdate(fT);
        }
        */
    }
    public  void render( ){

        for (Map.Entry<String, Component> componentEntry : components.entrySet()) {
            componentEntry.getValue().componentRender();
        }

        /*
        for( Component component : components){
            component.componentRender();
        }
        */
    }

    //_______________________________GETTER________________

    public Component getComponent(Class desiredClass) throws ClassNotFoundException {

        Component newComponent = components.get(desiredClass.getSimpleName());

        // -Daca ceri un obiect, si acesta este prezent, iti va fi returnat.
        // -Daca ceri un obiect, si un mostenitor al ecestuia este prezent, atunci iti va fi returnat acesta

        if( newComponent != null) {
            return newComponent;
        }
        else {
            for (Map.Entry<String, Component> componentEntry : components.entrySet()) {
                if (desiredClass.isAssignableFrom( componentEntry.getValue().getClass() ) ){
                    return componentEntry.getValue();
                }
            }
        }
        throw new ClassNotFoundException();
    }

    public GameContainer getGameContainer() {
        return gameContainer;
    }

}
