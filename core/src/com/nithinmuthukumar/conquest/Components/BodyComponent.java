package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Queue;

import static com.nithinmuthukumar.conquest.Globals.world;

//holds the box2d body of the entity
public class BodyComponent implements BaseComponent{
    //the box2d body
    public Body body;
    //these are the entities that the body has collided with
    public Queue<Entity> collidedEntities;
    //the direction and magnitude of knockback from an attack
    public Vector2 knockBack;


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
