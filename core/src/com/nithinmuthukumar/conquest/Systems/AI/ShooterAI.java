package com.nithinmuthukumar.conquest.Systems.AI;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Components.Identifiers.AllianceComponent;
import com.nithinmuthukumar.conquest.Components.Identifiers.ShooterComponent;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Globals;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import static com.nithinmuthukumar.conquest.Globals.*;

public class ShooterAI extends IteratingSystem {
    public ShooterAI() {
        super(Family.all(ShooterComponent.class, AIComponent.class).exclude(RemovalComponent.class).get(), 6);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = transformComp.get(entity);
        AIComponent ai = aiComp.get(entity);
        StateComponent state = stateComp.get(entity);
        AnimationComponent ani = animationComp.get(entity);
        FollowComponent follow = followComp.get(entity);
        TargetComponent target = targetComp.get(entity);

        if (follow.target == null) {
            state.action = Action.IDLE;
            Utils.findTarget(ai, transform, follow, entity);

            return;
        }
        if (transform.dst(target.getPos()) <= Globals.attackComp.get(entity).range && target.getPos() != null) {
            state.action = Action.BOWDRAW;
        } else {
            state.action = Action.WALK;
        }
        boolean finished = ani.isAnimationFinished(state.action, state.direction);
        if (finished && state.action == Action.BOWDRAW) {
            state.action = Action.BOWRELEASE;
            EntityFactory.createShot(attackComp.get(entity).weapon.make().add(Conquest.engine.createComponent(AllianceComponent.class).create(allianceComp.get(entity).side)), transform, target.getPos());
            ani.aniTime = 0;
        }
        if (finished && state.action == Action.BOWRELEASE) {
            state.action = Action.BOWDRAW;
            ani.aniTime = 0;
        }

    }
}
