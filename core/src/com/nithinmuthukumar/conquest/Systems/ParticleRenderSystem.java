package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.ParticleComponent;
import com.nithinmuthukumar.conquest.Helpers.Assets;
import com.nithinmuthukumar.conquest.Helpers.Globals;

import static com.nithinmuthukumar.conquest.Helpers.Globals.particleComp;

public class ParticleRenderSystem extends IteratingSystem {
    public ParticleRenderSystem() {
        super(Family.all(ParticleComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        particleComp.get(entity).effect.draw(Globals.batch);

    }
}
