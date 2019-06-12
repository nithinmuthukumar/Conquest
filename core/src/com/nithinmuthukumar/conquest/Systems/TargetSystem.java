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

public class TargetSystem extends IteratingSystem {
    public TargetSystem() {
        super(Family.all(TargetComponent.class, VelocityComponent.class).exclude(RemovalComponent.class).get(), 3);

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //sets angle of velocity based on where the target is


        /*if (followComp.has(entity) && followComp.get(entity).target != null) {
            if(followComp.get(entity).target.getComponents().size()==0){
                followComp.get(entity).target=null;
                return;
            }
            targetComp.get(entity).target.set(transformComp.get(followComp.get(entity).target).pos);
        }

         */


        Vector2 start = transformComp.get(entity).pos;

        Vector2 end = targetComp.get(entity).target;



        if(end==null)
            return;
        if (start.dst(end) < 5) {
            if (aiComp.has(entity)) {
                aiComp.get(entity).overallGoal = null;


            }
            targetComp.get(entity).target = null;
            velocityComp.get(entity).set(0, 0);


            return;
        }

        float angle=getTargetAngle(start,end);
        VelocityComponent velocity = Globals.velocityComp.get(entity);
        if(!start.equals(end)){
            if(rotatingComp.has(entity)){

                transformComp.get(entity).rotation = getTargetAngle(transformComp.get(entity).pos, targetComp.get(entity).target);
            }


            velocity.setAngle(angle);

        }





    }


}
