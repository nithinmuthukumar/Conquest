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
        super(Family.all(TowerComponent.class, AIComponent.class).exclude(RemovalComponent.class).get(), 10);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        TransformComponent transform = transformComp.get(entity);
        AIComponent ai = aiComp.get(entity);
        AttackComponent attack = attackComp.get(entity);
        TargetComponent target = targetComp.get(entity);
        Utils.findTarget(ai, transform, target, entity);
        if (target.target == null) {
            return;
        }
        EntityFactory.createShot(attack.weapon.make().add(Conquest.engine.createComponent(AllianceComponent.class).create(allianceComp.get(entity).side)), transform.pos, target.target);





    }
}