package com.apetrei.engine.objects.components;

public class TimerComponent extends  Component{

    float lifespawn;
    float timeAlive = 0;
    public TimerComponent(float lifespawn){
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
