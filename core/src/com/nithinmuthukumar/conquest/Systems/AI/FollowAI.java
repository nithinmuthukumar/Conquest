package com.nithinmuthukumar.conquest.Systems.AI;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import static com.nithinmuthukumar.conquest.Globals.*;

//chooses a target to follow
public class FollowAI extends IteratingSystem {
    public FollowAI() {
        super(Family.all(AIComponent.class, FollowComponent.class).exclude(RemovalComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = transformComp.get(entity);
        AIComponent ai = aiComp.get(entity);
        StateComponent state = stateComp.get(entity);
        FollowComponent follow = followComp.get(entity);
        //entity doesnt do anything if it is being born
        if (state.action == Action.BIRTH) {
            return;
        }
        //if the ai changes targets every iteration change target
        if (ai.isTargetChanger) {
            Utils.findFollow(ai, transform, follow, entity);
        }
        //checks if the target is valid or if it has been deleted or if it is to far for the entity to see
        if (follow.target == null || follow.target.getComponents().size() == 0 || Utils.getFollowDist(transform, follow) > ai.sightDistance) {
            //finds something new to follow
            if (!ai.isTargetChanger) {
                //if it has found a target return
                if (Utils.findFollow(ai, transform, follow, entity)) {
                    return;
                }
            }

            targetComp.get(entity).target = null;


            //if it gets here it has not found a target at all
            //if there is an overall goal the entity continues to walk
            if (ai.overallGoal == null) {

                state.action = Action.IDLE;
            } else {
                state.action = Action.WALK;
            }
        }

    }
}
