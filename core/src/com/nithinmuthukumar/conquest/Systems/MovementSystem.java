package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.nithinmuthukumar.conquest.Action;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Components.StateComponent;
import com.nithinmuthukumar.conquest.Components.VelocityComponent;

import static com.nithinmuthukumar.conquest.Utils.*;
//position velocity state
public class MovementSystem extends IteratingSystem {


    public MovementSystem(){
        super(Family.all(
                BodyComponent.class, VelocityComponent.class,
                StateComponent.class).get(),2);

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        VelocityComponent velocity=velocityComp.get(entity);
        BodyComponent body=bodyComp.get(entity);
        if(stateComp.get(entity).action== Action.WALK) {
            //print("MovementSystem",velocity.toString());
            body.body.setLinearVelocity((velocity.xCollide?0:velocity.x)*PPM,(velocity.yCollide?0:velocity.y)*PPM);
            if (velocity.xCollide || velocity.yCollide)
                body.body.setType(BodyDef.BodyType.StaticBody);
            else
                body.body.setType(BodyDef.BodyType.DynamicBody);

        } else
            body.body.setLinearVelocity(0,0);

    }
}
