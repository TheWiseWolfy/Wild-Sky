package com.apetrei.engine.ai;


import com.apetrei.engine.GameContainer;
import com.apetrei.engine.objects.GameObject;
import com.apetrei.engine.objects.ObjectTag;
import com.apetrei.engine.objects.components.EnemyComponent;
import com.apetrei.engine.objects.components.Rigidbody2D;
import com.apetrei.misc.Vector2;
import com.apetrei.misc.exceptions.ComponentMissingException;

import java.util.List;


public class enemyAI {

    private EnemyComponent enemyComponent;
    private GameContainer gameContainer;

    private GameObject objective;

    //Sense
    //Think
    //Act
    AIState currentState;

    public enemyAI( EnemyComponent enemyComponent, GameContainer gameContainer){
        this.enemyComponent = enemyComponent;
        this.gameContainer = gameContainer;
    }

   public void updateAI(){
        currentState = AIState.CHASE_OBJECTIVE;

        //CHASE TARGET
       List<GameObject> objectInRange = gameContainer.getObjectManager().findGameObjectInRange(enemyComponent.getParent(), 1500);

       for (var object : objectInRange) {
           if (object.hasTag(ObjectTag.enemy)) {
               float distance =  enemyComponent.distanceTo( object);
                if( distance > 500 ) {
                    currentState = AIState.CHASE_TARGET;
                    enemyComponent.chaseTarger(object);
                }else if( distance < 500){
                    currentState = AIState.FIGHT;
                    enemyComponent.destroy(object);
                }
           }
       }

       if( currentState == AIState.CHASE_OBJECTIVE){
           enemyComponent.chaseTarger( objective );
       }
       System.out.println(currentState.toString());
    }

    //_______________________SETTER_______________________

    public void setObjective(GameObject objective) {
        this.objective = objective;
    }
}

