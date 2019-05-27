package com.nithinmuthukumar.conquest.Helpers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Components.EquippableComponent;
import com.nithinmuthukumar.conquest.Components.Identifiers.BuiltComponent;
import com.nithinmuthukumar.conquest.Components.RenderableComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.UIDatas.BuildingData;
import com.nithinmuthukumar.conquest.UIDatas.ItemData;

import static com.nithinmuthukumar.conquest.Globals.*;

public class EntityFactory {
    //x and y must be bottom left coordinates of the image


    public static Entity createBuilding(int x, int y, BuildingData data) {
        Entity e;
        if(Assets.recipes.containsKey(data.name))
            e = Assets.recipes.get(data.name).make();
        else e = Conquest.engine.createEntity();
        e.add(Conquest.engine.createComponent(RenderableComponent.class).create(data.icon));
        e.add(Conquest.engine.createComponent(TransformComponent.class).create(x + data.icon.getRegionWidth() / 2, y + data.icon.getRegionHeight() / 2, 0, data.icon.getRegionWidth(), data.icon.getRegionHeight()));
        e.add(Conquest.engine.createComponent(BuiltComponent.class).create(data, x, y));
        Conquest.gameMap.addLayer(data.tileLayer, x, y, 0);
        Body body = bodyBuilder("StaticBody", x + data.icon.getRegionWidth() / 2, y + data.icon.getRegionHeight() / 2);
        for(RectangleMapObject object: data.collisionLayer){
            Rectangle rect=object.getRectangle();
            Filter f=new Filter();
            f.categoryBits= -1;
            f.maskBits= -1;
            if (object.getProperties().containsKey("collideinfo")) {
                f.groupIndex=(short)object.getProperties().get("collideinfo",Integer.class).intValue();
            }

            createRectFixture(rect.x - data.icon.getRegionWidth() / 2 + rect.width / 2,
                    rect.y - data.icon.getRegionHeight() / 2 + rect.height / 2, rect.width / 2, rect.height / 2, false, f, e, body);
        }

        e.add(Conquest.engine.createComponent(BodyComponent.class).create(body));


        System.out.println(builtComp.has(e));
        Conquest.engine.addEntity(e);
        return e;


    }

    public static Entity createItem(ItemData data, float x, float y) {
        Entity e = Conquest.engine.createEntity();
        e.add(Conquest.engine.createComponent(EquippableComponent.class).create(data));
        e.add(Conquest.engine.createComponent(RenderableComponent.class).create(data.icon));
        e.add(Conquest.engine.createComponent(TransformComponent.class).create(x, y, 0, data.icon.getRegionWidth(), data.icon.getRegionHeight()));
        Body body = bodyBuilder("StaticBody", x, y);
        createRectFixture(0, 0, data.icon.getRegionWidth() / 2, data.icon.getRegionHeight() / 2, true, new Filter(), e, body);
        BodyComponent bodyComponent = Conquest.engine.createComponent(BodyComponent.class).create(body);
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
        Conquest.engine.addEntity(e);
        return e;
    }

    public static Body bodyBuilder(String bodyType, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.valueOf(bodyType);
        bodyDef.position.set(x, y);
        return Conquest.world.createBody(bodyDef);


    }

    public static Body bodyBuilder(Entity e, String bodyType, String[] shapes, float[][] fixtureInfo, boolean[] isSensor) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.valueOf(bodyType);
        bodyDef.position.set(0, 0);
        Body body = Conquest.world.createBody(bodyDef);
        for (int i = 0; i < fixtureInfo.length; i++) {
            switch (shapes[i]) {
                case "Rectangle":
                    createRectFixture(fixtureInfo[i][0], fixtureInfo[i][1], fixtureInfo[i][2], fixtureInfo[i][3], isSensor[i], new Filter(), e, body);
                    break;
                case "Circle":
                    createCircleFixture(fixtureInfo[i][0], fixtureInfo[i][1], fixtureInfo[i][2], isSensor[i], new Filter(), e, body);


            }

        }

        return body;


    }

    public static Fixture createRectFixture(float x, float y, float hx, float hy, boolean isSensor, Filter filter, Entity e, Body body) {
        PolygonShape rect = new PolygonShape();
        rect.setAsBox(hx, hy, new Vector2(x, y), 0);
        return createFixture(rect, isSensor, filter, e, body);
    }

    public static Fixture createCircleFixture(float x, float y, float radius, boolean isSensor, Filter filter, Entity e, Body body) {
        CircleShape circle = new CircleShape();
        circle.setRadius(radius);
        circle.setPosition(new Vector2(x, y));
        return createFixture(circle, isSensor, filter, e, body);
    }

    public static Fixture createFixture(Shape shape, boolean isSensor, Filter filter, Entity e, Body body) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = isSensor;
        fixtureDef.filter.groupIndex = filter.groupIndex;
        fixtureDef.filter.categoryBits = filter.categoryBits;
        fixtureDef.filter.maskBits = filter.maskBits;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(e);
        return fixture;
    }

    public static Entity createMelee(Entity entity, Entity weapon) {
        TransformComponent transform = transformComp.get(entity);
        bodyComp.get(weapon).body.setTransform(transform.x, transform.y, 0);

        Utils.setUserData(weapon);
        Conquest.engine.addEntity(weapon);

        return weapon;
    }
}
