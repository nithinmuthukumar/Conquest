package com.nithinmuthukumar.conquest.Systems.AISystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.AIComponent;
import com.nithinmuthukumar.conquest.Components.SpawnerComponent;
import com.nithinmuthukumar.conquest.Helpers.SpawnNode;

import static com.nithinmuthukumar.conquest.Globals.spawnerComp;

public class SpawnAI extends IntervalIteratingSystem {

    public SpawnAI() {
        super(Family.all(SpawnerComponent.class, AIComponent.class).get(), 5);
    }


    @Override
    protected void processEntity(Entity entity) {
        SpawnerComponent spawner = spawnerComp.get(entity);
        spawner.inLine.addLast(new SpawnNode(Assets.recipes.get(spawner.spawnable.get(spawner.spawnableKeys[MathUtils.random(spawner.spawnableKeys.length - 1)])), 3));

    }
}
