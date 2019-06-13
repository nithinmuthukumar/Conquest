package com.nithinmuthukumar.conquest.Helpers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Globals;
import com.nithinmuthukumar.conquest.UIDatas.BuildingData;
import com.nithinmuthukumar.conquest.UIDatas.ItemData;

import static com.nithinmuthukumar.conquest.Globals.*;

public class EntityFactory {
    //x and y must be bottom left coordinates of the image


    public static Entity createBuilding(int x, int y, BuildingData data) {
        Entity e;

        if (Assets.recipes.containsKey(data.name)) {
            e = Assets.recipes.get(data.name).make();
            //adds the info obtained from the data to the entity

        } else {
            e = engine.createEntity();
        }


        e.add(engine.createComponent(RenderableComponent.class).create(data.icon));
        //position is the center of the image so it needs to be offset
        e.add(engine.createComponent(TransformComponent.class).create(x + data.icon.getRegionWidth() / 2, y + data.icon.getRegionHeight() / 2, 0, data.icon.getRegionWidth(), data.icon.getRegionHeight()));
        e.add(engine.createComponent(BuiltComponent.class).create(data, x, y));
        //add the layer to the map
        gameMap.addLayer(data.tileLayer, x, y);
        //create the body and add the fixtures obtained from the tiled map
        Body body = bodyBuilder("StaticBody", x + data.icon.getRegionWidth() / 2, y + data.icon.getRegionHeight() / 2);
        for(RectangleMapObject object: data.collisionLayer){
            Rectangle rect=object.getRectangle();
            createRectFixture(rect.x - data.icon.getRegionWidth() / 2 + rect.width / 2,
                    rect.y - data.icon.getRegionHeight() / 2 + rect.height / 2, rect.width / 2, rect.height / 2, false, e, body, 0, 0);
        }

        e.add(engine.createComponent(BodyComponent.class).create(body));


        System.out.println(builtComp.has(e));
        engine.addEntity(e);
        return e;


    }

    public static Entity createItem(ItemData data, float x, float y) {
        Entity e = engine.createEntity();
        e.add(engine.createComponent(EquippableComponent.class).create(data));
        e.add(engine.createComponent(RenderableComponent.class).create(Assets.itemPics.createSprite(data.getIconName())));
        e.add(engine.createComponent(TransformComponent.class).create(x, y, 0, data.icon.getRegionWidth(), data.icon.getRegionHeight()));
        e.add(engine.createComponent(ParticleComponent.class).create(Assets.effectPools.get(Globals.rarities[data.getRarity()] + "Effect").obtain(), null));
        Body body = bodyBuilder("KinematicBody", x, y);
        createRectFixture(0, 0, data.icon.getRegionWidth() / 2, data.icon.getRegionHeight() / 2, true, e, body, 0, 0);
        BodyComponent bodyComponent = engine.createComponent(BodyComponent.class).create(body);
        e.add(bodyComponent);
        return e;
    }

    public static Entity createShot(Entity e, Vector2 start, Vector2 target) {

        float angle=Utils.getTargetAngle(start,target);
        if(velocityComp.has(e))
            velocityComp.get(e).setAngle(angle);
        bodyComp.get(e).body.setTransform(start.x,start.y,angle);
        transformComp.get(e).rotation=angle;
        Utils.setUserData(e);
        return e;
    }

    public static Body bodyBuilder(String bodyType, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.valueOf(bodyType);
        bodyDef.position.set(x, y);
        return world.createBody(bodyDef);


    }

    public static Body bodyBuilder(Entity e, String bodyType, String[] shapes, float[][] fixtureInfo, boolean[] isSensor, int density, int friction) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.valueOf(bodyType);
        bodyDef.position.set(0, 0);
        Body body = world.createBody(bodyDef);
        for (int i = 0; i < fixtureInfo.length; i++) {
            switch (shapes[i]) {
                case "Rectangle":
                    createRectFixture(fixtureInfo[i][0], fixtureInfo[i][1], fixtureInfo[i][2], fixtureInfo[i][3], isSensor[i], e, body, density, friction);
                    break;
                case "Circle":
                    createCircleFixture(fixtureInfo[i][0], fixtureInfo[i][1], fixtureInfo[i][2], isSensor[i], e, body, density, friction);


            }

        }

        return body;


    }

    public static Fixture createRectFixture(float x, float y, float hx, float hy, boolean isSensor, Entity e, Body body, float density, float friction) {
        PolygonShape rect = new PolygonShape();
        rect.setAsBox(hx, hy, new Vector2(x, y), 0);
        return createFixture(rect, isSensor, e, body, density, friction);
    }

    public static Fixture createCircleFixture(float x, float y, float radius, boolean isSensor, Entity e, Body body, float density, float friction) {
        CircleShape circle = new CircleShape();
        circle.setRadius(radius);
        circle.setPosition(new Vector2(x, y));
        return createFixture(circle, isSensor, e, body, density, friction);
    }

    public static Fixture createFixture(Shape shape, boolean isSensor, Entity e, Body body, float density, float friction) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = isSensor;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(e);
        return fixture;
    }

    public static Entity createMelee(Entity entity, Entity weapon) {
        TransformComponent transform = transformComp.get(entity);
        bodyComp.get(weapon).body.setTransform(transform.pos.x, transform.pos.y, 0);

        Utils.setUserData(weapon);
        engine.addEntity(weapon);

        return weapon;
    }
}
