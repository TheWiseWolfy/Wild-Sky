package com.apetrei.misc.observer;

import com.apetrei.engine.objects.GameObject;

public interface ObjectManagerObserver {

    public void newObjectUpdate(GameObject gameObject);
    public void objectDeletedUpdate(GameObject gameObject);

}
