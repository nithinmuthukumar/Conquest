package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.SpawnerComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.Server.SpawnMessage;

import static com.nithinmuthukumar.conquest.Globals.*;

public class SpawnSystem extends IteratingSystem {
    public SpawnSystem() {
        super(Family.all(SpawnerComponent.class, TransformComponent.class).get(),5);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = transformComp.get(entity);
        SpawnerComponent spawner= spawnerComp.get(entity);
        if(!spawner.inLine.isEmpty()){
            if(spawner.inLine.first().timer<=0){
                Conquest.client.getClient().sendTCP(new SpawnMessage(allianceComp.get(entity).side, spawner.inLine.removeFirst().name, transform.pos.x, transform.pos.y));



            }else{
                spawner.inLine.first().timer-=deltaTime;
            }
        }

    }
}
