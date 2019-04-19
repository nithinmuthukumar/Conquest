package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.ParticleComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.Helpers.Assets;
import com.nithinmuthukumar.conquest.Helpers.Globals;

import static com.nithinmuthukumar.conquest.Helpers.Globals.*;

public class ParticleRenderer {


    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent t=transformComp.get(entity);
        particleComp.get(entity).effect.setPosition(t.x,t.y);
        particleComp.get(entity).effect.draw(Globals.batch,deltaTime);

    }
}
