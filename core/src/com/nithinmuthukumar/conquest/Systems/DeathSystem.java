package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.HealthComponent;
import com.nithinmuthukumar.conquest.Components.RemovalComponent;
import com.nithinmuthukumar.conquest.Globals;

import static com.nithinmuthukumar.conquest.Globals.healthComp;
import static com.nithinmuthukumar.conquest.Globals.transformComp;

public class DeathSystem extends IteratingSystem {
    public DeathSystem() {
        super(Family.all(HealthComponent.class).exclude(RemovalComponent.class).get(), 9);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //checks whether the entity health is below 0 in which case it is killed by adding the Removal Component
        if (healthComp.get(entity).health <= 0) {
            entity.add(((PooledEngine) getEngine()).createComponent(RemovalComponent.class).create(2));
            ParticleEffectPool.PooledEffect effect = Assets.effectPools.get("deathEffect").obtain();
            effect.setPosition(transformComp.get(entity).pos.x, transformComp.get(entity).pos.y);
            Globals.renderSystem.addParticleRequest(effect);

        }

    }
}
