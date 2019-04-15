package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.HealthComponent;
import com.nithinmuthukumar.conquest.Components.RemovalComponent;

import static com.nithinmuthukumar.conquest.Helpers.Globals.healthComp;

public class HealthSystem extends IteratingSystem {
    public HealthSystem() {
        super(Family.all(HealthComponent.class).exclude(RemovalComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        System.out.println(healthComp.get(entity).health);
        if(healthComp.get(entity).health<=0){
            PooledEngine engine=(PooledEngine)(getEngine());

            entity.add(engine.createComponent(RemovalComponent.class).create(2f));
        }

    }
}
