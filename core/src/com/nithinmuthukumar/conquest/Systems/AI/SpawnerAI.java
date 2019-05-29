package com.nithinmuthukumar.conquest.Systems.AI;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.nithinmuthukumar.conquest.Components.AIComponent;
import com.nithinmuthukumar.conquest.Components.RemovalComponent;
import com.nithinmuthukumar.conquest.Components.SpawnerComponent;
import com.nithinmuthukumar.conquest.Helpers.SpawnNode;

import static com.nithinmuthukumar.conquest.Globals.spawnerComp;

public class SpawnerAI extends IteratingSystem {
    public SpawnerAI() {
        super(Family.all(SpawnerComponent.class, AIComponent.class).exclude(RemovalComponent.class).get(), 6);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SpawnerComponent spawner = spawnerComp.get(entity);
        spawner.inLine.addLast(new SpawnNode(spawner.spawnable.get(spawner.spawnableKeys[MathUtils.random(spawner.spawnableKeys.length - 1)]), 3));

    }
}
