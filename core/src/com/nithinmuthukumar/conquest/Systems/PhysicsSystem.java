package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Components.RemovalComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.Globals;

import static com.nithinmuthukumar.conquest.Globals.bodyComp;
import static com.nithinmuthukumar.conquest.Globals.transformComp;

public class PhysicsSystem extends IntervalIteratingSystem {

    public PhysicsSystem() {
        //iterates through all the bodies and sets the position of the entity to match its Box2d position
        super(Family.all(BodyComponent.class, TransformComponent.class).exclude(RemovalComponent.class).get(), 1 / 60f, 8);

    }

    @Override
    protected void updateInterval() {
        Globals.world.step(1 / 60f, 6, 2);

        super.updateInterval();
    }

    @Override
    protected void processEntity(Entity entity) {
        TransformComponent transform = transformComp.get(entity);
        BodyComponent body=bodyComp.get(entity);
        transform.pos.x = body.body.getPosition().x;
        transform.pos.y = body.body.getPosition().y;
        //sets the rotation of the body to the rotation of the entity
        body.body.setTransform(body.body.getWorldCenter(), MathUtils.degreesToRadians*transform.rotation);




    }
}
