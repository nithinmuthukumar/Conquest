package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Queue;

import static com.nithinmuthukumar.conquest.Conquest.world;

public class BodyComponent implements BaseComponent{

    public Body body;
    public Queue<Entity> collidedEntities;


    @Override
    public BaseComponent create() {
        collidedEntities = new Queue<>();




        return this;
    }

    public BodyComponent create(Body body) {
        this.body = body;
        collidedEntities = new Queue<>();
        return this;
    }


    @Override
    public void reset() {
        world.destroyBody(body);
        collidedEntities = null;

    }



}
