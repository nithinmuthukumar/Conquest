package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Components.RemovalComponent;
import com.nithinmuthukumar.conquest.Components.StateComponent;
import com.nithinmuthukumar.conquest.Components.VelocityComponent;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Globals;

import static com.nithinmuthukumar.conquest.Globals.stateComp;

//position velocity state
public class MovementSystem extends IteratingSystem {

    public MovementSystem(){
        super(Family.all(BodyComponent.class, VelocityComponent.class).exclude(RemovalComponent.class).get(), 6);

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        VelocityComponent velocity = Globals.velocityComp.get(entity);

        BodyComponent body = Globals.bodyComp.get(entity);

        StateComponent state = stateComp.get(entity);
        //if there is knockback on the body the entities velocity is overriden and it moves in the direction of knockback
        if (body.knockBack != null && body.knockBack.len2() > 1) {
            body.body.setLinearVelocity(body.knockBack.cpy().scl(100000));
            //the knock back is reduced until its length is less than 1
            body.knockBack.scl(0.4f, 0.4f);
            return;

        } else {
            body.knockBack = null;
        }
        //if the entity is walking or has no Action its velocity is set to its velocity component
        if (state == null || state.action == Action.WALK||state.action==Action.NONE) {


            body.body.setLinearVelocity((velocity.x) * Globals.PPM, ( velocity.y) * Globals.PPM);


        } else
            //this means that the entity is not moving so velocity is set to zero
            body.body.setLinearVelocity(0,0);


    }
}
