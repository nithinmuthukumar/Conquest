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
        //looks into whether there is anything in the spawners queue
        if(!spawner.inLine.isEmpty()){
            //the timer is reduced on the spawner node until it reaches 0
            if(spawner.inLine.first().timer<=0){
                //checks if the spawner is acting out of an ai behaviour. If it is, the same thing is happening on the other conquestClient,
                //if there is one
                //if there isn't that means the player put it there
                if (!aiComp.has(entity))
                    conquestClient.getClient().sendTCP(new SpawnMessage(spawner.inLine.removeFirst().name, transform.pos.x, transform.pos.y));
                else
                    Utils.spawn(allianceComp.get(entity).side, spawner.inLine.removeFirst().name, transform.pos.x, transform.pos.y);

            }else{
                spawner.inLine.first().timer-=deltaTime;
            }
        }

    }
}
