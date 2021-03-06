package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.DecayComponent;
import com.nithinmuthukumar.conquest.Components.RemovalComponent;
import com.nithinmuthukumar.conquest.Globals;

import static com.nithinmuthukumar.conquest.Globals.decayComp;

public class DecaySystem extends IteratingSystem {
    public DecaySystem() {
        super(Family.all(DecayComponent.class).get(), 9);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //the decay countdown happens until it reaches 0 where it gets the removal component
        decayComp.get(entity).countDown-=deltaTime;
        if(decayComp.get(entity).countDown<=0){
            entity.add(Globals.engine.createComponent(RemovalComponent.class).create(0));

        }

    }
}
