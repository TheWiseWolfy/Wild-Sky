package com.apetrei.engine.components;
import com.apetrei.engine.GameObject;

import java.io.Serializable;

/*!
 * Asta e o reprezentare generica a unei componente, care atunci cand este imperecheata cu un gameObject, incapsuleaza  logica
 * jocului si referintele necesare acesteia. O componenta este serializabila,
 * adica poate folosi direct doar tipuri de date serializabile
 */
public abstract class Component implements Serializable {

    protected GameObject parent = null;

    public Component(){
    }

    /*!
    * Lazy contructor - proceduri care trebuiesc facute odata ce .
    * Ex: Daca pui un rigidbody , si apoi un sprite, ultimul pas e sa le dai bind
    * TODO: Look into not needing this anymore
    */

    public abstract void componentInit();


    /*!
     * Update the logic of the component at the propper time
     */
    public abstract void componentUpdate( double fT);

    /*!
     * Render the component at the propper time
     */
    public abstract void componentRender( );

    //______________________________SETTERS_AND_GETTERS__________________________

    public GameObject getParent() {
        return parent;
    }

    /*!
     * Used in the lazy initialization, for creating components before their parents exist.
     */
    public void setParent(GameObject parent) {
        this.parent = parent;
    }

}
