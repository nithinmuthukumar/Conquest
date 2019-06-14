package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.nithinmuthukumar.conquest.Components.RemovalComponent;
import com.nithinmuthukumar.conquest.Components.TargetComponent;
import com.nithinmuthukumar.conquest.Components.VelocityComponent;
import com.nithinmuthukumar.conquest.Globals;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import static com.nithinmuthukumar.conquest.Globals.*;

//sets the angle of the velocity based on where the target is
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


        //if there is no end, exit
        if(end==null)
            return;

        //if the goal has been reached the overall goal is set to null so that the entity doesn't try over and over to reach the point
        if (aiComp.has(entity)) {
            if (aiComp.get(entity).overallGoal != null && start.dst(aiComp.get(entity).overallGoal) < 5)
                aiComp.get(entity).overallGoal = null;

        } else {
            //if the goal is reached the entity stops movind
            if (start.dst(end) < 5) {
                targetComp.get(entity).target = null;
                velocityComp.get(entity).set(0, 0);
                return;
            }

        }
        //get the angle that the entity should be traveling at and set it
        float angle = Utils.getTargetAngle(start, end);
        VelocityComponent velocity = Globals.velocityComp.get(entity);


        velocity.setAngle(angle);







    }


}
