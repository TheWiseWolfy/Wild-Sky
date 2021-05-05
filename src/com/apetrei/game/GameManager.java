package com.apetrei.game;

import com.apetrei.engine.GameContainer;
import com.apetrei.engine.objects.GameObject;
import com.apetrei.engine.objects.ObjectTag;
import com.apetrei.engine.objects.components.*;
import com.apetrei.engine.physics.primitives.colliders.ConvexCollider;
import com.apetrei.engine.objects.components.Rigidbody2D;
import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.misc.Vector2;

import java.util.ArrayList;
import java.util.List;

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
