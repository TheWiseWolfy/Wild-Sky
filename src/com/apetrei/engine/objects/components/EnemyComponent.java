package com.apetrei.engine.objects.components;

import com.apetrei.engine.ConfigHandler;

public class EnemyComponent extends Component implements HealthInterface{

    int healt = 100;
    int maxHealt = 100;

    public void addHealt( int value){
        if( healt + value <= maxHealt)  {
            healt +=value;
        }else {
            healt = maxHealt;
        }
    }

    public void substactHealt( int value){
        if( healt - value >= 0)  {
            healt -=value;
        }else {
            healt = 0;
        }
    }

    @Override
    public void componentInit() {

    }

    @Override
    public void componentUpdate(double fT) {
        if( healt <= 0){
            this.parent.kill();
        }
    }

    @Override
    public void componentRender() {

    }
}
