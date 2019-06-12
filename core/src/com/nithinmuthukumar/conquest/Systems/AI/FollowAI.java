package com.nithinmuthukumar.conquest.Systems.AI;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import static com.nithinmuthukumar.conquest.Globals.*;

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
        if (ai.isTargetChanger) {
            Utils.findFollow(ai, transform, follow, entity);
        }
        if (follow.target == null || follow.target.getComponents().size() == 0 || Utils.getFollowDist(transform, follow) > ai.sightDistance) {
            if (!ai.isTargetChanger) {
                if (Utils.findFollow(ai, transform, follow, entity)) {
                    return;
                }
            }
            targetComp.get(entity).target = null;




            if (ai.overallGoal == null) {

                state.action = Action.IDLE;
            } else {
                state.action = Action.WALK;
            }
        }

    }
}
