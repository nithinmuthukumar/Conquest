package com.nithinmuthukumar.conquest.Systems.AI;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import static com.nithinmuthukumar.conquest.Globals.*;

//changes the state of the entities based on if it has found a target or if it needs to attack
public class MeleeAI extends IteratingSystem {
    public MeleeAI() {
        super(Family.all(MeleeComponent.class, AIComponent.class).exclude(RemovalComponent.class).get(), 1);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = transformComp.get(entity);
        StateComponent state = stateComp.get(entity);
        AnimationComponent ani = animationComp.get(entity);
        FollowComponent follow = followComp.get(entity);

        if (state.action == Action.BIRTH) {
            return;
        }
        if (follow.target == null || follow.target.getComponents().size() == 0) {
            return;
        }


        if (Utils.getFollowDist(transform, follow) <= attackComp.get(entity).range) {
            state.action = Action.ATTACK;

        } else {
            state.action = Action.WALK;
        }
        boolean finished = ani.isAnimationFinished(state.action, state.direction);
        if (finished && state.action == Action.ATTACK) {
            Utils.setMeleeTransform(entity, EntityFactory.createMelee(entity, attackComp.get(entity).weapon.make().add(engine.createComponent(AllianceComponent.class).create(allianceComp.get(entity).side))));
            ani.aniTime = 0;

        }

    }
}
