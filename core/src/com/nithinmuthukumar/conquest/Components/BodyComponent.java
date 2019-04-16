package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;

import static com.nithinmuthukumar.conquest.Helpers.Globals.bodyComp;
import static com.nithinmuthukumar.conquest.Helpers.Globals.world;

public class BodyComponent implements Component, Pool.Poolable {
    public Body body;
    public Entity collidedEntity;
    public BodyComponent create(Body body){
        this.body=body;
        return this;
    }
    @Override
    public void reset() {
        world.destroyBody(body);
        collidedEntity=null;

    }
}
