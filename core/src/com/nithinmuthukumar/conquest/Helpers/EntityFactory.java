package com.nithinmuthukumar.conquest.Helpers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Components.EquippableComponent;
import com.nithinmuthukumar.conquest.Components.Identifiers.BuiltComponent;
import com.nithinmuthukumar.conquest.Components.RenderableComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.GameMap;
import com.nithinmuthukumar.conquest.UIDatas.BuildingData;
import com.nithinmuthukumar.conquest.UIDatas.ItemData;

import static com.nithinmuthukumar.conquest.Globals.*;

public class EntityFactory {
    //x and y must be bottom left coordinates of the image
    public static Entity createBuilding(int x, int y, BuildingData data, GameMap gameMap) {
        Entity e;
        if(Assets.recipes.containsKey(data.name))
            e = Assets.recipes.get(data.name).make();
        else e=engine.createEntity();
        e.add(engine.createComponent(RenderableComponent.class).create(data.icon));
        e.add(engine.createComponent(TransformComponent.class).create(x + data.icon.getRegionWidth() / 2, y + data.icon.getRegionHeight() / 2, 0, data.icon.getRegionWidth(), data.icon.getRegionHeight()));
        e.add(engine.createComponent(BuiltComponent.class).create(data));
        gameMap.addLayer(data.tileLayer, x, y, 0);
        BodyComponent body = engine.createComponent(BodyComponent.class).create(x + data.icon.getRegionWidth() / 2, y + data.icon.getRegionHeight() / 2, BodyDef.BodyType.StaticBody);
        for(RectangleMapObject object: data.collisionLayer){
            Rectangle rect=object.getRectangle();
            Filter f=new Filter();
            f.categoryBits= -1;
            f.maskBits= -1;
            if(object.getProperties().containsKey("collideinfo"))
                f.groupIndex=(short)object.getProperties().get("collideinfo",Integer.class).intValue();

            createRectFixture(body.body, rect.x - data.icon.getRegionWidth() / 2 + rect.width / 2,
                    rect.y - data.icon.getRegionHeight() / 2 + rect.height / 2, rect.width / 2, rect.height / 2, 0, 0, f, e);
        }
        e.add(body);



        engine.addEntity(e);
        return e;


    }

    public static Entity createItem(ItemData data) {
        Entity e = engine.createEntity();
        e.add(engine.createComponent(EquippableComponent.class).create(data));
        e.add(engine.createComponent(RenderableComponent.class).create(data.icon));
        e.add(engine.createComponent(TransformComponent.class).create(0, 0, 0, data.icon.getRegionWidth(), data.icon.getRegionHeight()));
        BodyComponent body = engine.createComponent(BodyComponent.class).create(0, 0, BodyDef.BodyType.StaticBody);
        createRectFixture(body.body, 0, 0, data.icon.getRegionWidth() / 2, data.icon.getRegionHeight() / 2, 0, 0, new Filter(), e);
        e.add(body);
        return e;
    }

    public static Entity createShot(Entity e, Vector2 start, Vector2 target) {

        float angle=Utils.getTargetAngle(start,target);
        if(velocityComp.has(e))
            velocityComp.get(e).setAngle(angle);
        bodyComp.get(e).body.setTransform(start.x,start.y,angle);
        transformComp.get(e).rotation=angle;
        Utils.setUserData(e);
        engine.addEntity(e);
        return e;
    }

    public static void createRectFixture(Body body, float x, float y, float hx, float hy, float angle, float density, Filter f, Entity e) {

        PolygonShape rect=new PolygonShape();
        rect.setAsBox(hx,hy,new Vector2(x,y),angle);
        fixtureDef.shape = rect;
        fixtureDef.density=density;
        fixtureDef.filter.groupIndex=f.groupIndex;
        fixtureDef.filter.categoryBits=f.categoryBits;
        fixtureDef.filter.maskBits=f.maskBits;
        body.createFixture(fixtureDef).setUserData(e);

    }

    public static Entity createMelee(Entity entity, Entity weapon) {
        TransformComponent transform = transformComp.get(entity);
        bodyComp.get(weapon).body.setTransform(transform.x, transform.y, 0);

        Utils.setUserData(weapon);
        engine.addEntity(weapon);

        return weapon;
    }
}
