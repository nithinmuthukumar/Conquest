package com.nithinmuthukumar.conquest.Systems.AI;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import static com.nithinmuthukumar.conquest.Globals.*;

public class ShooterAI extends IteratingSystem {
    public ShooterAI() {
        super(Family.all(ShooterComponent.class, AIComponent.class, FollowComponent.class).exclude(RemovalComponent.class).get(), 1);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = transformComp.get(entity);
        StateComponent state = stateComp.get(entity);
        AnimationComponent ani = animationComp.get(entity);
        FollowComponent follow = followComp.get(entity);
        TargetComponent target = targetComp.get(entity);
        if (follow.target == null || follow.target.getComponents().size() == 0 || target.target == null) {


            return;
        }


        if (Utils.getFollowDist(transform, follow) <= attackComp.get(entity).range) {
            state.action = Action.DRAW;
        } else {
            state.action = Action.WALK;
        }
        boolean finished = ani.isAnimationFinished(state.action, state.direction);
        if (finished && state.action == Action.DRAW) {
            state.action = Action.RELEASE;
            Entity shot = EntityFactory.createShot(attackComp.get(entity).weapon.make().add(Conquest.engine.createComponent(AllianceComponent.class).create(allianceComp.get(entity).side)), transform.pos, target.target);

            getEngine().addEntity(shot);
            ani.aniTime = 0;
        }
        if (finished && state.action == Action.RELEASE) {
            state.action = Action.DRAW;
            ani.aniTime = 0;
        }

    }
}
