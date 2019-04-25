package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Components.Identifiers.PlayerComponent;
import com.nithinmuthukumar.conquest.Components.SpawnerComponent;
import com.nithinmuthukumar.conquest.Components.TargetComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.Helpers.SpawnNode;
import com.nithinmuthukumar.conquest.Helpers.Utils;
import com.nithinmuthukumar.conquest.Player;

import static com.nithinmuthukumar.conquest.Globals.*;

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
                Entity e=spawner.inLine.removeFirst().data.make();

                BodyComponent body=bodyComp.get(e);

                body.body.setTransform(pos.x,pos.y,body.body.getAngle());


                e.add(engine.createComponent(TargetComponent.class).create(transformComp.get(engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).first())));

                Utils.setUserData(e);
                engine.addEntity(e);


            }else{
                spawner.inLine.first().timer-=deltaTime;
            }
        }else{
            spawner.inLine.addLast(new SpawnNode(spawner.spawnable.first(),2));
        }

    }
}
