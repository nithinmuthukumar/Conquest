package com.nithinmuthukumar.conquest.Systems.AI;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Components.Identifiers.AllianceComponent;
import com.nithinmuthukumar.conquest.Components.Identifiers.MeleeComponent;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import static com.nithinmuthukumar.conquest.Globals.*;

public class MeleeAI extends IteratingSystem {
    public MeleeAI() {
        super(Family.all(MeleeComponent.class, AIComponent.class).exclude(RemovalComponent.class).get(), 6);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = transformComp.get(entity);
        AIComponent ai = aiComp.get(entity);
        StateComponent state = stateComp.get(entity);
        AnimationComponent ani = animationComp.get(entity);
        TargetComponent target = targetComp.get(entity);
        FollowComponent follow = followComp.get(entity);


        if (follow.target == null || Utils.getFollowDist(transform, follow) > ai.sightDistance) {
            Utils.findFollow(ai, transform, follow, entity);
            target.target = null;
            state.action = Action.IDLE;
            return;
        }
        if (Utils.getFollowDist(transform, follow) <= attackComp.get(entity).range) {
            state.action = Action.ATTACK;

        } else {
            state.action = Action.WALK;
        }
        boolean finished = ani.isAnimationFinished(state.action, state.direction);
        if (finished && state.action == Action.ATTACK) {
            EntityFactory.createMelee(entity, attackComp.get(entity).weapon.make().add(Conquest.engine.createComponent(AllianceComponent.class).create(allianceComp.get(entity).side)));
            ani.aniTime = 0;

        }

    }
}