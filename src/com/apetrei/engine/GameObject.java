package com.apetrei.engine;

import com.apetrei.engine.components.Component;
import com.apetrei.misc.exceptions.ComponentMissingException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/*!
 * O clasa care abstractizeaza toate obiectele din joc sub forma unor colectii de componente interconectate.
 * Orice data stocata aici, sau in orice tip de Compoent trebuie sa fie serializabila pentru a permite
 * stocarea externa a stari jocului
 */

public class GameObject implements Serializable {

    Set<String> tags = new TreeSet<String>();

    //protected LinkedList<GameObject> children;
    HashMap<String, Component> components;

    protected GameContainer gameContainer;

    public boolean active = false;
    private String uniqueTag;

    public GameObject(GameContainer gameContainer){
        active = true;
        components = new HashMap<String, Component>();
        this.gameContainer = gameContainer;
    }



    public void addComponent(Component newComponent ){
        newComponent.setParent(this);
        newComponent.componentInit();
        components.put(newComponent.getClass().getSimpleName() ,newComponent);
    }

    public boolean hasComponent(Class component){
        return  components.containsKey( component.getSimpleName() );
    }

    public boolean isActive() {
        return active;
    }

    //Fuctii de actualizare
    public  void update(double fT){

        for (Map.Entry<String, Component> componentEntry : components.entrySet()) {
            componentEntry.getValue().componentUpdate(fT);
        }

    }
    public  void render( ){

        for (Map.Entry<String, Component> componentEntry : components.entrySet()) {
            componentEntry.getValue().componentRender();
        }
    }
    //_______________________________TAGS____________________________

    public void addTag(String string){
        tags.add(string);
    }

    public boolean hasTag(String tag){
        return tags.contains( tag);
    }


    //_______________________________GETTER__________________________

    public Component getComponent(Class desiredClass) throws ComponentMissingException {

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
        throw new ComponentMissingException(desiredClass.getSimpleName());
    }

    public GameContainer getGameContainer() {
        return gameContainer;
    }

}
