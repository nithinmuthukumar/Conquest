package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.ParticleComponent;
import com.nithinmuthukumar.conquest.Components.StateComponent;

import static com.nithinmuthukumar.conquest.Globals.particleComp;
import static com.nithinmuthukumar.conquest.Globals.stateComp;

//sets the state of the particle based on the state of the entity
public class StateParticleSystem extends IteratingSystem {
    public StateParticleSystem() {
        super(Family.all(StateComponent.class, ParticleComponent.class).get(), 5);
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        particleComp.get(entity).action=stateComp.get(entity).action;

    }
}
