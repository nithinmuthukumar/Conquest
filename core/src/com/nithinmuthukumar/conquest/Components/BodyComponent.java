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

import static com.nithinmuthukumar.conquest.Globals.*;

public class BodyComponent implements BaseComponent{

    public Body body;
    public Entity collidedEntity;
    private int x;
    private int y;
    private int fixtureX;
    private int fixtureY;
    private int hx;
    private int hy;
    private short group;
    private short mask;
    private String bodyType;
    private int angle;


    @Override
    public BaseComponent create() {
        bodyDef.type=BodyDef.BodyType.valueOf(bodyType);
        bodyDef.position.x=x;
        bodyDef.position.y=y;

        PolygonShape rect=new PolygonShape();
        rect.setAsBox(hx,hx, new Vector2(fixtureX,fixtureY),angle);
        fixtureDef.shape=rect;
        fixtureDef.filter.groupIndex=group;
        fixtureDef.filter.maskBits=mask;
        body=world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        


        return this;
    }
    public BodyComponent create(int x, int y, BodyDef.BodyType type) {
        bodyDef.type= type;
        bodyDef.position.x=x;
        bodyDef.position.y=y;
        bodyDef.fixedRotation=true;
        body=world.createBody(bodyDef);
        return this;
    }
    public void addFixture(){

    }


    @Override
    public void reset() {
        world.destroyBody(body);
        collidedEntity=null;

    }



}
