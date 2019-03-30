package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.World;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;

import static com.nithinmuthukumar.conquest.Utils.bodyComp;
import static com.nithinmuthukumar.conquest.Utils.transformComp;

public class PhysicsSystem extends IntervalIteratingSystem {
    private World world;
    private OrthographicCamera camera;

    public PhysicsSystem(World world){
        super(Family.all(BodyComponent.class, TransformComponent.class).get(), 1 / 60f, 3);
        this.world=world;

    }

    @Override
    protected void updateInterval() {
        world.step(1/60f,6,2);

        super.updateInterval();
    }

    @Override
    protected void processEntity(Entity entity) {
        TransformComponent position = transformComp.get(entity);
        BodyComponent body=bodyComp.get(entity);
        position.x=body.body.getPosition().x;
        position.y=body.body.getPosition().y;




    }
}
