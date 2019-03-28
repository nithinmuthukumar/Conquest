package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Action;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Utils;

import static com.nithinmuthukumar.conquest.Utils.*;
//position velocity state
public class MovementSystem extends IteratingSystem {


    public MovementSystem(){
        super(Family.all(
                BodyComponent.class, VelocityComponent.class,
                StateComponent.class).get());

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        VelocityComponent velocity=velocityComp.get(entity);
        BodyComponent body=bodyComp.get(entity);
        if(stateComp.get(entity).action== Action.WALK&&!body.collided) {
            body.body.setLinearVelocity(velocity.x*PPM,velocity.y*PPM);
        }
        else{
            body.body.setLinearVelocity(0,0);

        }









    }
}
