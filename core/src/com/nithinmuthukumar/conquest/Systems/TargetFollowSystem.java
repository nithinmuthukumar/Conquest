package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Helpers.Globals;

import static com.nithinmuthukumar.conquest.Helpers.Globals.*;
import static com.nithinmuthukumar.conquest.Utils.getTargetAngle;

public class TargetFollowSystem extends IteratingSystem {
    public TargetFollowSystem() {
        super(Family.all(TargetComponent.class,VelocityComponent.class).exclude(RemovalComponent.class).get());

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        Vector2 start=transformComp.get(entity);
        Vector2 end=targetComp.get(entity).target;
        float angle=getTargetAngle(start,end);
        VelocityComponent velocity = Globals.velocityComp.get(entity);
        if(!start.equals(end)){
            if(rotatingComp.has(entity)){

                transformComp.get(entity).rotation=getTargetAngle(transformComp.get(entity),targetComp.get(entity).target);
            }

            velocity.setAngle(angle);

        }

    }
}
