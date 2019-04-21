package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Pool;

import static com.nithinmuthukumar.conquest.Globals.world;

public class BodyComponent implements BaseComponent{
    private static BodyDef bodyDef;
    private static FixtureDef fixtureDef;
    public Body body;
    public Entity collidedEntity;
    @Override
    public BaseComponent create(JsonValue args) {
        JsonValue bodyArgs=args.get("body");
        switch (bodyArgs.getString("type")){
            case "kinematic":
                bodyDef.type= BodyDef.BodyType.KinematicBody;
                break;
            case "static":
                bodyDef.type= BodyDef.BodyType.StaticBody;
                break;
            case "dynamic":
                bodyDef.type= BodyDef.BodyType.DynamicBody;
        }
        bodyDef.position.x=bodyArgs.getFloat("x");
        bodyDef.position.y=bodyArgs.getFloat("y");
        for(JsonValue fixtureArgs: args.get("fixture")){
            PolygonShape rect=new PolygonShape();
            rect.setAsBox(fixtureArgs.getFloat("hx"),fixtureArgs.getFloat("hy")
                    , new Vector2(fixtureArgs.getFloat("x"),fixtureArgs.getFloat("y")),fixtureArgs.getFloat("angle"));
            fixtureDef.shape=rect;
            fixtureDef.filter.groupIndex=fixtureArgs.getShort("group");
            fixtureDef.filter.categoryBits=fixtureArgs.getShort("category");
            fixtureDef.filter.maskBits=fixtureArgs.getShort("mask");

        }
        return this;
    }


    @Override
    public void reset() {
        world.destroyBody(body);
        collidedEntity=null;

    }


    public Component create(Body body) {
        this.body=body;
        return this;
    }
}
