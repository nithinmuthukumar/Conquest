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
        //sets angle of velocity based on where the target is
        if (followComp.has(entity) && followComp.get(entity).target != null) {
            if (targetComp.get(entity).target == null) {
                targetComp.get(entity).target = new Vector2(transformComp.get(followComp.get(entity).target));
            } else {
                targetComp.get(entity).target.set(transformComp.get(followComp.get(entity).target));
            }
        }
        Vector2 start=transformComp.get(entity);

        Vector2 end = targetComp.get(entity).target;

        if(end==null)
            return;
        float angle=getTargetAngle(start,end);
        VelocityComponent velocity = Globals.velocityComp.get(entity);
        if(!start.equals(end)){
            if(rotatingComp.has(entity)){

                transformComp.get(entity).rotation = getTargetAngle(transformComp.get(entity), targetComp.get(entity).target);
            }


            velocity.setAngle(angle);

        }





    }


}
