package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.pfa.PathFinder;
import com.nithinmuthukumar.conquest.Components.EnemyComponent;
import com.nithinmuthukumar.conquest.Components.PlayerComponent;

public class AISystem extends IteratingSystem {
    public AISystem(){
        super(Family.all(EnemyComponent.class).exclude(PlayerComponent.class).get());
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {


    }
}
