package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Components.Identifiers.AllianceComponent;
import com.nithinmuthukumar.conquest.Components.SpawnerComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import static com.nithinmuthukumar.conquest.Globals.*;

public class SpawnSystem extends IteratingSystem {
    public SpawnSystem() {
        super(Family.all(SpawnerComponent.class, TransformComponent.class).get(),5);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent pos=transformComp.get(entity);
        SpawnerComponent spawner= spawnerComp.get(entity);
        if(!spawner.inLine.isEmpty()){
            if(spawner.inLine.first().timer<=0){
                Entity e=spawner.inLine.removeFirst().data.make();
                e.add(Conquest.engine.createComponent(AllianceComponent.class).create(allianceComp.get(entity).side));



                BodyComponent body=bodyComp.get(e);

                body.body.setTransform(pos.x, pos.y - 40, body.body.getAngle());


                Utils.setUserData(e);
                Conquest.engine.addEntity(e);


            }else{
                spawner.inLine.first().timer-=deltaTime;
            }
        }

    }
}
