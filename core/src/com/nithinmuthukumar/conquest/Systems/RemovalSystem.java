package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Array;
import com.nithinmuthukumar.conquest.Components.RemovalComponent;


import static com.nithinmuthukumar.conquest.Helpers.Globals.removalComp;

public class RemovalSystem extends IteratingSystem {


    public RemovalSystem() {
        super(Family.all(RemovalComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        RemovalComponent removal = removalComp.get(entity);
        if (removal.countDown <= 0) {

            getEngine().removeEntity(entity);

        }
        removal.countDown -= deltaTime;



    }
}
