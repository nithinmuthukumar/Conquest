package com.nithinmuthukumar.conquest.Systems.AISystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.AIComponent;
import com.nithinmuthukumar.conquest.Components.Identifiers.ArcherComponent;

public class ArcherAI extends IteratingSystem {

    public ArcherAI() {
        super(Family.all(ArcherComponent.class, AIComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {


    }
}
