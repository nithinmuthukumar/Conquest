package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.HealthComponent;
import com.nithinmuthukumar.conquest.Components.RemovalComponent;

import static com.nithinmuthukumar.conquest.Globals.healthComp;

public class DeathSystem extends IteratingSystem {
    public DeathSystem() {
        super(Family.all(HealthComponent.class).exclude(RemovalComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        int health = healthComp.get(entity).health;
        if (health <= 0) {
            entity.add(((PooledEngine) getEngine()).createComponent(RemovalComponent.class).create(2));

        }

    }
}
