package com.apetrei.engine.ai;


import com.apetrei.engine.GameContainer;
import com.apetrei.engine.event.GlobalEvent;
import com.apetrei.engine.objects.GameObject;
import com.apetrei.engine.objects.ObjectTag;
import com.apetrei.engine.objects.components.EnemyComponent;

import java.util.List;

/*!
 * O clasa care descrie comportamentul unui inamic dupa o lista de parametri locali.
 */
public class enemyAI {

    private EnemyComponent enemyComponent;
    private GameContainer gameContainer;

    private GameObject objective;
    AIState currentState;

    //Many values
    int visionRange = 1500;
    int attckDistance = 600;
    int fightRange = 400;
    int attackObjectiveRange = 1000;
    int mentainDistanceToObjective = 800;

    public enemyAI( EnemyComponent enemyComponent, GameContainer gameContainer){
        this.enemyComponent = enemyComponent;
        this.gameContainer = gameContainer;
    }

   public void updateAI(){
        currentState = AIState.CHASE_OBJECTIVE;

        //CHASE TARGET
       List<GameObject> objectInRange = gameContainer.getObjectManager().findGameObjectInRange(enemyComponent.getParent(), visionRange);

       for (var object : objectInRange) {
           if (object.hasTag(ObjectTag.player)) {
               float distance =  enemyComponent.distanceTo( object);
                if( distance > attckDistance) {
                    currentState = AIState.CHASE_TARGET;
                    enemyComponent.chaseTarget(object);
                }
                else if( distance < attckDistance){
                    currentState = AIState.FIGHT;
                    enemyComponent.destroy(object);
                    enemyComponent.mentainDistance(object,fightRange, attckDistance);
                }
           }
       }

       if( currentState == AIState.CHASE_OBJECTIVE && objective != null){
           float distanceToObjective =  enemyComponent.distanceTo( objective);
           enemyComponent.chaseTarget( objective );

           if (distanceToObjective <= attackObjectiveRange && !objective.hasTag(ObjectTag.player)){
               enemyComponent.destroy( objective);
               enemyComponent.mentainDistance(objective,mentainDistanceToObjective,attackObjectiveRange);
           }
       }
    }

    //_______________________SETTER_______________________

    public void setAttackDistance(int attckDistance) {
        this.attckDistance = attckDistance;
    }

    public void setFightRange(int fightRange) {
        this.fightRange = fightRange;
    }

    public void setObjective(GameObject objective) {
        this.objective = objective;
    }

    public void setAttackObjectiveRange(int attackObjectiveRange) {
        this.attackObjectiveRange = attackObjectiveRange;
    }

}

