package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.nithinmuthukumar.conquest.Components.RemovalComponent;
import com.nithinmuthukumar.conquest.Components.TargetComponent;
import com.nithinmuthukumar.conquest.Components.VelocityComponent;
import com.nithinmuthukumar.conquest.Globals;

import static com.nithinmuthukumar.conquest.Globals.*;
import static com.nithinmuthukumar.conquest.Helpers.Utils.getTargetAngle;

public class TargetFollowSystem extends IteratingSystem {
    public TargetFollowSystem() {
        super(Family.all(TargetComponent.class,VelocityComponent.class).exclude(RemovalComponent.class).get(),10);

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {



        Vector2 start=transformComp.get(entity);

        Vector2 end = targetComp.get(entity).getPos();

        if(end==null)
            return;
        float angle=getTargetAngle(start,end);
        VelocityComponent velocity = Globals.velocityComp.get(entity);
        if(!start.equals(end)){
            if(rotatingComp.has(entity)){

                transformComp.get(entity).rotation = getTargetAngle(transformComp.get(entity), targetComp.get(entity).getPos());
            }


            velocity.setAngle(angle);

        }





    }


}
