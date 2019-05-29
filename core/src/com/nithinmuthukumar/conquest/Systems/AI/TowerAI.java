package com.nithinmuthukumar.conquest.Systems.AI;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Components.Identifiers.AllianceComponent;
import com.nithinmuthukumar.conquest.Components.Identifiers.TowerComponent;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import static com.nithinmuthukumar.conquest.Globals.*;

public class TowerAI extends IteratingSystem {
    public TowerAI() {
        super(Family.all(TowerComponent.class, AIComponent.class).exclude(RemovalComponent.class).get(), 6);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TargetComponent target = targetComp.get(entity);
        TransformComponent transform = transformComp.get(entity);
        AIComponent ai = aiComp.get(entity);
        StateComponent state = stateComp.get(entity);
        AnimationComponent ani = animationComp.get(entity);
        FollowComponent follow = followComp.get(entity);
        if (target == null) {

            Utils.findTarget(ai, transform, follow, entity);


        }
        if (target != null) {

            EntityFactory.createShot(attackComp.get(entity).weapon.make().add(Conquest.engine.createComponent(AllianceComponent.class).create(allianceComp.get(entity).side)), transform, target.getPos());
        }

    }
}
