package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.SpawnerComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.Helpers.Utils;
import com.nithinmuthukumar.conquest.Server.SpawnMessage;

import static com.nithinmuthukumar.conquest.Globals.*;

public class SpawnSystem extends IteratingSystem {
    public SpawnSystem() {
        super(Family.all(SpawnerComponent.class, TransformComponent.class).get(), 14);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = transformComp.get(entity);
        SpawnerComponent spawner= spawnerComp.get(entity);

        if(!spawner.inLine.isEmpty()){
            if(spawner.inLine.first().timer<=0){
                if (!aiComp.has(entity))
                    client.getClient().sendTCP(new SpawnMessage(spawner.inLine.removeFirst().name, transform.pos.x, transform.pos.y));
                else
                    Utils.spawn(allianceComp.get(entity).side, spawner.inLine.removeFirst().name, transform.pos.x, transform.pos.y);



            }else{
                spawner.inLine.first().timer-=deltaTime;
            }
        }

    }
}
