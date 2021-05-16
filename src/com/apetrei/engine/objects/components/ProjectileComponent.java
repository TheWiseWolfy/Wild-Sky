package com.apetrei.engine.objects.components;

public class ProjectileComponent extends  Component{

    float lifespawn;
    float timeAlive = 0;
    public ProjectileComponent(float lifespawn){
        this.lifespawn = lifespawn;
    }

    @Override
    public void componentInit() {

    }

    @Override
    public void componentUpdate(double fT) {

        timeAlive += fT;

        if( timeAlive > lifespawn ){
            this.parent.kill();
        }
    }

    @Override
    public void componentRender() {

    }
}
