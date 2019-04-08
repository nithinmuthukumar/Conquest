package com.nithinmuthukumar.conquest;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.nithinmuthukumar.conquest.Components.*;

import static com.nithinmuthukumar.conquest.Constants.engine;
import static com.nithinmuthukumar.conquest.Constants.world;
public class EntityFactory {
    public static void createPlayer() {
        Entity e=new Entity();
        e.add(new TransformComponent(500, 500, 0, 24, 30));
        e.add(new AnimationComponent("characters/hero/", 0.07f, 6));
        e.add(new PlayerComponent());
        e.add(new StateComponent(8));
        e.add(new VelocityComponent(1.2f));
        e.add(new RenderableComponent());
        e.add(new CameraComponent());
        Body body = createBody(500, 500, BodyDef.BodyType.DynamicBody, 0);


        addRectFixture(body, 0, 0, 10, 10, 0, 50);
        e.add(new BodyComponent(body));
        engine.addEntity(e);
    }

    public static void createKnight(int x, int y, Entity target) {
        Entity e = new Entity();
        e.add(new TransformComponent(x, y, 0, 32, 32));
        e.add(new TargetComponent(target));
        e.add(new AnimationComponent("characters/knight/", 0.06f, 4));
        e.add(new EnemyComponent());
        e.add(new StateComponent(2));
        e.add(new VelocityComponent(1f));
        e.add(new RenderableComponent());
        e.add(new FighterComponent(50, null));
        Body body = createBody(x, y, BodyDef.BodyType.DynamicBody, 0);
        addRectFixture(body, 0, 0, 14, 14, 0, 0);
        e.add(new BodyComponent(body));
        engine.addEntity(e);


    }

    public static Body createBody(int x, int y, BodyDef.BodyType type, float density) {
        BodyDef bodyDef=new BodyDef();
        bodyDef.type= type;
        bodyDef.position.x=x;
        bodyDef.position.y=y;
        bodyDef.fixedRotation=true;
        return world.createBody(bodyDef);
    }
    public static void addRectFixture(Body body,float x,float y,float hx,float hy,float angle,float density){
        PolygonShape rect=new PolygonShape();
        rect.setAsBox(hx,hy,new Vector2(x,y),angle);
        body.createFixture(rect,density);


    }

    public static void createMap(BuildingData data, int x, int y, GameMap gameMap) {
        int mapX = x - data.image.getWidth() / 2;
        int mapY = y - data.image.getHeight() / 2;
        Entity e = new Entity();
        e.add(new RenderableComponent(data.image));
        e.add(new TransformComponent(x, y, 0, data.image.getWidth(), data.image.getHeight()));
        gameMap.addLayer(data.tileLayer, mapX, mapY, data.image, 0);
        engine.addEntity(e);

    }

    //need to add component that says its available in shop
    public static void createMap(int x, int y, String file, GameMap collisionLayer) {
        System.out.println(Assets.manager.contains(file+"/map.tmx"));
        TiledMap map=Assets.manager.get(file+"/map.tmx");

        TiledMapImageLayer image=(TiledMapImageLayer)(map.getLayers().get("image"));
        int mapX = x - image.getTextureRegion().getRegionWidth() / 2;
        int mapY = y - image.getTextureRegion().getRegionHeight() / 2;
        Entity e=new Entity();
        e.add(new RenderableComponent(image.getTextureRegion().getTexture()));
        e.add(new TransformComponent(x, y, 0, image.getTextureRegion().getRegionWidth(), image.getTextureRegion().getRegionHeight()));
        engine.addEntity(e);
        collisionLayer.addLayer((TiledMapTileLayer) map.getLayers().get("tileinfo"), mapX, mapY, image.getTextureRegion().getTexture(), 0);
        //still have to finish this
        //going to get another tmx file from this and recursively add it to the map
        for(MapObject object: map.getLayers().get("renderinfo").getObjects()){
            if(object.getName().equals("roof")){
                e=new Entity();
                e.add(new RenderableComponent(Assets.manager.get(file+"/assets/"+object.getProperties().get("asset").toString(),Texture.class)));
                e.add(new TransformComponent(mapX + object.getProperties().get("x", Integer.class),
                        mapY + object.getProperties().get("y", Integer.class), 1, 0, 0));
                e.add(new RoofComponent());
                engine.addEntity(e);
            }

        }


    }

    public static void createMapNavigator(int initX, int initY, int deviation) {
        Entity e=new Entity();
        e.add(new TransformComponent(initX, initY, 0, 0, 0));
        e.add(new MouseComponent());
        e.add(new CameraComponent());
        engine.addEntity(e);
    }

    public static void createBkg(String path) {
        Entity e=new Entity();
        Texture bkg=Assets.manager.get(path);
        e.add(new RenderableComponent(bkg));
        e.add(new TransformComponent(bkg.getWidth() / 2, bkg.getHeight() / 2, -1, bkg.getWidth(), bkg.getHeight()));
        engine.addEntity(e);
    }
}
