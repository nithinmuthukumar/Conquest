package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.World;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Components.PositionComponent;
import com.nithinmuthukumar.conquest.Utils;

import static com.nithinmuthukumar.conquest.Utils.*;

public class PhysicsSystem extends IntervalIteratingSystem {
    private World world;
    private OrthographicCamera camera;

    public PhysicsSystem(World world){
        super(Family.all(BodyComponent.class, PositionComponent.class).get(),1/60f);
        this.world=world;

    }

    @Override
    protected void updateInterval() {
        world.step(1/60f,6,2);

        super.updateInterval();
    }

    @Override
    protected void processEntity(Entity entity) {
        PositionComponent position=positionComp.get(entity);
        BodyComponent body=bodyComp.get(entity);
        position.x=body.body.getPosition().x;
        position.y=body.body.getPosition().y;




    }
}
