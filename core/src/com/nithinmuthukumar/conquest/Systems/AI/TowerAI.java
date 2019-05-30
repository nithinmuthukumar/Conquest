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
        FollowComponent follow = followComp.get(entity);
        AttackComponent attack = attackComp.get(entity);

        if (target == null) {

            if (Utils.findTarget(ai, transform, follow, entity)) {
                attack.timer = attack.coolDown;
            }

        } else if (attack.timer > attack.coolDown) {
            attack.timer = 0;
            if (target.target != null) {

                EntityFactory.createShot(attack.weapon.make().add(Conquest.engine.createComponent(AllianceComponent.class).create(allianceComp.get(entity).side)), transform, target.target);
            }
        }


    }
}
