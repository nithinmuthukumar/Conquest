package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.nithinmuthukumar.conquest.Components.AnimationComponent;
import com.nithinmuthukumar.conquest.Components.Identifiers.EnemyComponent;
import com.nithinmuthukumar.conquest.Components.Identifiers.PlayerComponent;
import com.nithinmuthukumar.conquest.Components.RemovalComponent;
import com.nithinmuthukumar.conquest.Components.StateComponent;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Globals;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import static com.nithinmuthukumar.conquest.Globals.*;

public class AISystem extends IteratingSystem {
    public AISystem(){
        super(Family.all(EnemyComponent.class).exclude(PlayerComponent.class, RemovalComponent.class).get(),6);
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        StateComponent state = Globals.stateComp.get(entity);

        Vector2 start = Globals.transformComp.get(entity);
        Vector2 target = Globals.targetComp.get(entity).target;
        if (start.dst(target) <= Globals.fighterComp.get(entity).range) {
            state.action = Action.ATTACK;


        } else {
            state.action = Action.WALK;
        }
        if(state.action==Action.ATTACK){
            AnimationComponent animation=animationComp.get(entity);
            if(animation.get(state.action,state.direction).isAnimationFinished(animation.aniTime)){
                animation.aniTime=0;
                Entity e=fighterComp.get(entity).weapon.make();
                Globals.engine.addEntity(e);
                float angle=Utils.getTargetAngle(start,target);
                if(velocityComp.has(e))
                    velocityComp.get(e).setAngle(angle);
                bodyComp.get(e).body.setTransform(start.x,start.y,angle);
                transformComp.get(e).rotation=angle;
                Utils.setUserData(e);
                engine.addEntity(e);

            }
        }

    }
}
