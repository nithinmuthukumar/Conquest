package com.nithinmuthukumar.conquest.Helpers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Datas.BuildingData;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Enums.Direction;
import com.nithinmuthukumar.conquest.GameMap;

import static com.nithinmuthukumar.conquest.Helpers.Globals.*;

public class EntityFactory {
    private static FixtureDef fixtureDef = new FixtureDef();
    private static BodyDef bodyDef = new BodyDef();
    public static void createPlayer() {
        Entity e=engine.createEntity();

        e.add(engine.createComponent(TransformComponent.class).create(500, 500, 0, 24, 30));
        e.add(engine.createComponent(HealthComponent.class).create(21));
        e.add(engine.createComponent(AnimationComponent.class).create("characters/hero/", 0.07f, 6));
        e.add(engine.createComponent(PlayerComponent.class));
        e.add(engine.createComponent(StateComponent.class).create(8, Action.IDLE, Direction.DOWN));
        e.add(engine.createComponent(VelocityComponent.class).create(1.2f));
        e.add(engine.createComponent(RenderableComponent.class));
        e.add(engine.createComponent(CameraComponent.class));
        e.add(engine.createComponent(ParticleComponent.class).create(Assets.effect));


        Body body = createBody(500, 500, BodyDef.BodyType.DynamicBody, 0);

        Filter f=new Filter();
        f.categoryBits=BIT_PLAYER;
        f.maskBits=BIT_ENEMY | BIT_ENEMYWEAPON;
        f.groupIndex=0;
        createRectFixture(body, 0, 0, 10, 10, 0, 50, f, e);
        e.add(engine.createComponent(BodyComponent.class).create(body));
        engine.addEntity(e);
    }

    public static void createKnight(int x, int y, Entity target) {
        Entity e = engine.createEntity();
        e.add(engine.createComponent(TransformComponent.class).create(x, y, 0, 32, 32));
        e.add(engine.createComponent(TargetComponent.class).create(transformComp.get(target)));
        e.add(engine.createComponent(AnimationComponent.class).create("characters/knight/", 0.06f, 4));
        e.add(engine.createComponent(EnemyComponent.class));
        e.add(engine.createComponent(StateComponent.class).create(2,Action.WALK,Direction.DOWN));
        e.add(engine.createComponent(VelocityComponent.class).create(1f));
        e.add(engine.createComponent(RenderableComponent.class));
        e.add(engine.createComponent(FighterComponent.class).create(50, null));
        e.add(engine.createComponent(HealthComponent.class).create(20));
        Body body = createBody(x, y, BodyDef.BodyType.DynamicBody, 0);
        Filter f=new Filter();
        f.categoryBits=BIT_ENEMY;
        f.maskBits=BIT_PLAYER | BIT_PLAYERWEAPON;
        f.groupIndex=0;

        createRectFixture(body, 0, 0, 14, 14, 0, 0 ,f , e);
        e.add(engine.createComponent(BodyComponent.class).create(body));
        engine.addEntity(e);


    }

    public static Body createBody(int x, int y, BodyDef.BodyType type, float density) {
        bodyDef.type= type;
        bodyDef.position.x=x;
        bodyDef.position.y=y;
        bodyDef.fixedRotation=true;
        return world.createBody(bodyDef);
    }

    public static void createRectFixture(Body body, float x, float y, float hx, float hy, float angle, float density, Filter f,Entity e) {

        PolygonShape rect=new PolygonShape();
        rect.setAsBox(hx,hy,new Vector2(x,y),angle);
        fixtureDef.shape = rect;
        fixtureDef.filter.groupIndex=f.groupIndex;
        fixtureDef.filter.categoryBits=f.categoryBits;
        fixtureDef.filter.maskBits=f.maskBits;
        body.createFixture(fixtureDef).setUserData(e);

    }

    //x and y must be bottom left coordinates of the image
    public static void createMap(BuildingData data, int x, int y, GameMap gameMap) {
        int mapX = MathUtils.round(x - data.getImage().getWidth() / 2);
        int mapY = MathUtils.round(y - data.getImage().getHeight() / 2);
        Entity e = new Entity();
        e.add(engine.createComponent(RenderableComponent.class).create(data.getImage()));
        e.add(engine.createComponent(TransformComponent.class).create(x, y, 0, data.getImage().getWidth(), data.getImage().getHeight()));
        gameMap.addLayer(data.getTileLayer(), mapX, mapY, data.getImage(), 0);
        Body body = createBody(x, y, BodyDef.BodyType.StaticBody, 0);
        for(RectangleMapObject object: data.getCollisionLayer()){
            Rectangle rect=object.getRectangle();
            Filter f=new Filter();
            f.categoryBits= -1;
            f.maskBits= -1;
            if(object.getProperties().containsKey("collideinfo"))
                f.groupIndex=(short)object.getProperties().get("collideinfo",Integer.class).intValue();
            createRectFixture(body, rect.x- data.getImage().getWidth() / 2+rect.width/2, rect.y- data.getImage().getHeight() / 2+rect.height/2,
                    rect.width/2, rect.height/2, 0, 0, f, e);
        }
        e.add(engine.createComponent(BodyComponent.class).create(body));

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
        e.add(engine.createComponent(RenderableComponent.class).create(image.getTextureRegion().getTexture()));
        e.add(engine.createComponent(TransformComponent.class).create(x, y, 0, image.getTextureRegion().getRegionWidth(), image.getTextureRegion().getRegionHeight()));

        collisionLayer.addLayer((TiledMapTileLayer) map.getLayers().get("tileinfo"), mapX, mapY, image.getTextureRegion().getTexture(), 0);
        Body body = createBody(x, y, BodyDef.BodyType.StaticBody, 0);
        for(RectangleMapObject object: map.getLayers().get("collisioninfo").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=object.getRectangle();
            Filter f=new Filter();
            f.categoryBits=-1;
            f.maskBits= -1;
            if(object.getProperties().containsKey("collideinfo"))
                f.groupIndex=(short)object.getProperties().get("collideinfo",Integer.class).intValue();


            createRectFixture(body, rect.x-image.getTextureRegion().getRegionWidth() / 2+rect.width/2, rect.y-image.getTextureRegion().getRegionHeight() / 2+rect.height/2,
                    rect.width/2, rect.height/2, 0, 0, f, e);
        }
        e.add(engine.createComponent(BodyComponent.class).create(body));
        engine.addEntity(e);
        /*
            if(object.getName().equals("roof")){
                e=new Entity();
                e.add(RenderableComponent(Assets.manager.get(file+"/assets/"+object.getProperties().get("asset").toString(),Texture.class)));
                e.add(new TransformComponent(mapX + object.getProperties().get("x", Integer.class),
                        mapY + object.getProperties().get("y", Integer.class), 1, 0, 0));
                e.add(new RoofComponent());
                engine.addEntity(e);
            }

             */






    }

    public static void createRenderable(Texture texture, float x, float y) {
        Entity e = new Entity();
        e.add(engine.createComponent(RenderableComponent.class).create(texture));
        e.add(engine.createComponent(TransformComponent.class).create(x, y, 0, texture.getWidth(), texture.getHeight()));
        engine.addEntity(e);
    }

    public static void createMapNavigator(int initX, int initY, int deviation) {
        Entity e=new Entity();
        e.add(engine.createComponent(TransformComponent.class).create(initX, initY, 0, 0, 0));
        e.add(new MouseComponent());
        e.add(new CameraComponent());
        engine.addEntity(e);
    }

    public static void createBkg(String path) {
        Entity e=new Entity();
        Texture bkg=Assets.manager.get(path);
        e.add(engine.createComponent(RenderableComponent.class).create(bkg));
        e.add(engine.createComponent(TransformComponent.class).create(bkg.getWidth() / 2, bkg.getHeight() / 2, -1, bkg.getWidth(), bkg.getHeight()));
        engine.addEntity(e);
    }

    public static void createArrow(float startX, float startY, float endX, float endY) {
        Entity e = engine.createEntity();
        Texture t = Assets.manager.get("ui stuff/arrow.png", Texture.class);
        e.add(engine.createComponent(TransformComponent.class).create(startX, startY, 0, t.getWidth(), t.getHeight()));
        e.add(engine.createComponent(RenderableComponent.class).create(t));
        e.add(engine.createComponent(TargetComponent.class).create(new Vector2(endX, endY)));
        e.add(engine.createComponent(VelocityComponent.class).create(1f));
        e.add(engine.createComponent(CollisionRemoveComponent.class));
        e.add(engine.createComponent(RotatingComponent.class));
        e.add(engine.createComponent(WeaponComponent.class).create(5));
        Body body = createBody(MathUtils.round(startX), MathUtils.round(startY), BodyDef.BodyType.DynamicBody, 0);
        Filter f=new Filter();
        f.categoryBits=BIT_PLAYERWEAPON;
        f.maskBits=BIT_ENEMY | BIT_ENEMYWEAPON;
        createRectFixture(body, 0, 0, 14, 14, 0, 2000, f, e);

        e.add(engine.createComponent(BodyComponent.class).create(body));

        engine.addEntity(e);
    }
}
