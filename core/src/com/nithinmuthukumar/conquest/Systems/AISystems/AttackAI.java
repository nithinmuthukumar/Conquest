package com.nithinmuthukumar.conquest.Systems.AISystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Globals;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import static com.nithinmuthukumar.conquest.Globals.*;

public class AttackAI extends IteratingSystem {
    private Rectangle r=new Rectangle();
    public AttackAI(){
        super(Family.all(AttackComponent.class, TargetComponent.class).exclude(RemovalComponent.class).get(),6);
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Vector2 start = Globals.transformComp.get(entity);


        StateComponent state = Globals.stateComp.get(entity);
        if(targetComp.get(entity).target==null||!perspectiveContains(start,targetComp.get(entity).target)){
            targetComp.get(entity).target=null;


            boolean found = findTarget(entity, Utils.getOppositeFamily(entity));
            if(!found) {


                state.action = Action.IDLE;
                return;
            }
        }

        Vector2 target = Globals.targetComp.get(entity).target;
        if (start.dst(target) <= Globals.attackComp.get(entity).range) {
            state.action = Action.ATTACK;


        } else {
            state.action = Action.WALK;
        }
        if (state.action == Action.ATTACK) {
            AnimationComponent animation=animationComp.get(entity);
            if(animation.get(state.action,state.direction).isAnimationFinished(animation.aniTime)){
                animation.aniTime=0;
                Entity e= attackComp.get(entity).weapon.make();
                EntityFactory.createShot(e,start,target);

            }
        }


    }
    public boolean findTarget(Entity entity,Family f){
        TransformComponent entityPos=transformComp.get(entity);

        for(Entity potentialTarget:engine.getEntitiesFor(f)){
            TransformComponent ptPos=transformComp.get(potentialTarget);
            //this allows the enemy to spot all entities that are within the screen when it is in the center
            if(perspectiveContains(entityPos,ptPos)){

                targetComp.get(entity).target=ptPos;
                return true;
            }



        }
        return false;


    }
    //perspective contains checks if the attacker can see the target as if it was in the middle of the screen
    //the screen size however is smaller to give the player an advantage in seeing the enemy before it sees him
    public boolean perspectiveContains(Vector2 e,Vector2 t){
        return r.set(e.x - Gdx.graphics.getWidth() / 2, e.y - Gdx.graphics.getHeight() / 2,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight()).contains(t);
    }
}
