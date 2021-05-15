package com.apetrei.game;

import com.apetrei.providers.GameContainer;

//!MAIN
public class GameManager {

    public static void main(String[] args) {
        GameContainer gameContainer = new GameContainer();
        gameContainer.start();
    }

    //Aici e o fuctie de testare care demonstreaza cum sunt create initial nivelele din joc.
    //Odata ce obiectele astea sunt serializate in ObjectManager, pot fi incarcate direct de pe disk
    //fara sa fie nevoie de vreo metoda de genul asta.

}
