package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;

import static com.nithinmuthukumar.conquest.Conquest.world;

public class BodyComponent implements BaseComponent{

    public Body body;
    public Entity collidedEntity;


    @Override
    public BaseComponent create() {




        return this;
    }

    public BodyComponent create(Body body) {
        this.body = body;
        return this;
    }


    @Override
    public void reset() {
        world.destroyBody(body);
        collidedEntity=null;

    }



}
