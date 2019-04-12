package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Components.StateComponent;
import com.nithinmuthukumar.conquest.Components.VelocityComponent;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Helpers.Globals;

import static com.nithinmuthukumar.conquest.Helpers.Globals.stateComp;

//position velocity state
public class MovementSystem extends IteratingSystem {


    public MovementSystem(){
        super(Family.all(BodyComponent.class, VelocityComponent.class).get(), 2);

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        VelocityComponent velocity = Globals.velocityComp.get(entity);
        BodyComponent body = Globals.bodyComp.get(entity);
        StateComponent state = stateComp.get(entity);
        if (state == null || state.action == Action.WALK) {
            body.body.setLinearVelocity((velocity.xCollide ? 0 : velocity.x) * Globals.PPM, (velocity.yCollide ? 0 : velocity.y) * Globals.PPM);

        } else
            body.body.setLinearVelocity(0,0);

    }
}
