package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.SpawnerComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.Helpers.SpawnNode;

import static com.nithinmuthukumar.conquest.Globals.spawnComp;
import static com.nithinmuthukumar.conquest.Globals.transformComp;

public class SpawnSystem extends IteratingSystem {
    public SpawnSystem() {
        super(Family.all(SpawnerComponent.class, TransformComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent pos=transformComp.get(entity);
        SpawnerComponent spawner=spawnComp.get(entity);
        if(!spawner.inLine.isEmpty()){
            if(spawner.inLine.first().timer<=0){
                //spawner.inLine.removeFirst().data.spawn(pos.x,pos.y);
            }else{
                spawner.inLine.first().timer-=deltaTime;
            }
        }else{
            //spawner.inLine.addLast(new Spawn(spawner.spawnable.first(),4));
        }

    }
}
