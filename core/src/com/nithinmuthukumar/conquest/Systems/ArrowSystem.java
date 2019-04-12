package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.ArrowComponent;

import static com.nithinmuthukumar.conquest.Helpers.Globals.bodyComp;
import static com.nithinmuthukumar.conquest.Helpers.Globals.world;

public class ArrowSystem extends IteratingSystem {

    public ArrowSystem() {
        super(Family.all(ArrowComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (bodyComp.get(entity).collidedEntity != null) {
            getEngine().removeEntity(entity);
            world.destroyBody(bodyComp.get(entity).body);

        }

    }
}
