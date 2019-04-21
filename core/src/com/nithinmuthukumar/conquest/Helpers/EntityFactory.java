package com.nithinmuthukumar.conquest.Helpers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Enums.Direction;
import com.nithinmuthukumar.conquest.GameMap;

import static com.nithinmuthukumar.conquest.Globals.*;

public class EntityFactory {
    private static FixtureDef fixtureDef = new FixtureDef();
    private static BodyDef bodyDef = new BodyDef();
    public static void createPlayer() {
        Entity e=engine.createEntity();

        e.add(engine.createComponent(TransformComponent.class).create(500, 500, 0, 24, 30));
        e.add(engine.createComponent(HealthComponent.class).create(21));
        e.add(engine.createComponent(AnimationComponent.class).create("characters/hero/", 0.07f));
        e.add(engine.createComponent(PlayerComponent.class));
        e.add(engine.createComponent(StateComponent.class).create(8, Action.IDLE, Direction.DOWN));
        e.add(engine.createComponent(VelocityComponent.class).create(1.2f));
        e.add(engine.createComponent(RenderableComponent.class));
        e.add(engine.createComponent(CameraComponent.class));

        ObjectMap<Action, ParticleEffectPool.PooledEffect> effects=new ObjectMap<>();
        effects.put(Action.WALK,Assets.effectPools.get("burnout").obtain());
        e.add(engine.createComponent(ParticleComponent.class).create(null,effects));


        Body body = createBody(500, 500, BodyDef.BodyType.DynamicBody);

        Filter f=new Filter();
        f.categoryBits=BIT_PLAYER;
        f.maskBits=BIT_ENEMY | BIT_ENEMYWEAPON;
        f.groupIndex=0;
        createRectFixture(body, 0, 0, 10, 10, 0, 50, f, e);
        e.add(engine.createComponent(BodyComponent.class).create(body));
        engine.addEntity(e);
    }



    public static void createKnight(float x, float y, Entity target) {
        Entity e = engine.createEntity();
        e.add(engine.createComponent(TransformComponent.class).create(x, y, 0, 32, 32));
        e.add(engine.createComponent(TargetComponent.class).create(transformComp.get(target)));
        e.add(engine.createComponent(AnimationComponent.class).create("characters/knight/", 0.06f));
        e.add(engine.createComponent(EnemyComponent.class));
        e.add(engine.createComponent(StateComponent.class).create(2,Action.WALK,Direction.DOWN));
        e.add(engine.createComponent(VelocityComponent.class).create(1f));
        e.add(engine.createComponent(RenderableComponent.class));
        e.add(engine.createComponent(FighterComponent.class).create(50, null));
        e.add(engine.createComponent(HealthComponent.class).create(20));
        Body body = createBody((int)x, (int)y, BodyDef.BodyType.DynamicBody);
        Filter f=new Filter();
        f.categoryBits=BIT_ENEMY;
        f.maskBits=BIT_PLAYER | BIT_PLAYERWEAPON|BIT_ENEMY;
        f.groupIndex=0;

        createRectFixture(body, 0, 0, 14, 14, 0, 0 ,f , e);
        e.add(engine.createComponent(BodyComponent.class).create(body));
        engine.addEntity(e);


    }

    public static Body createBody(int x, int y, BodyDef.BodyType type) {
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
        fixtureDef.density=density;
        fixtureDef.filter.groupIndex=f.groupIndex;
        fixtureDef.filter.categoryBits=f.categoryBits;
        fixtureDef.filter.maskBits=f.maskBits;
        body.createFixture(fixtureDef).setUserData(e);

    }

    //x and y must be bottom left coordinates of the image
    public static Entity createMap(BuildingData data, int x, int y, GameMap gameMap) {
        int mapX = MathUtils.round(x - data.getImage().getWidth() / 2);
        int mapY = MathUtils.round(y - data.getImage().getHeight() / 2);
        Entity e = new Entity();
        e.add(engine.createComponent(RenderableComponent.class).create(data.getImage()));
        e.add(engine.createComponent(TransformComponent.class).create(x, y, 0, data.getImage().getWidth(), data.getImage().getHeight()));
        gameMap.addLayer(data.getTileLayer(), mapX, mapY, data.getImage(), 0);
        Body body = createBody(x, y, BodyDef.BodyType.StaticBody);
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
        return e;

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
        e.add(engine.createComponent(VelocityComponent.class).create(10f));
        e.add(engine.createComponent(CollisionRemoveComponent.class));
        e.add(engine.createComponent(RotatingComponent.class));
        e.add(engine.createComponent(WeaponComponent.class).create(5));
        Body body = createBody(MathUtils.round(startX), MathUtils.round(startY), BodyDef.BodyType.DynamicBody);
        Filter f=new Filter();
        f.categoryBits=BIT_PLAYERWEAPON;
        f.maskBits=BIT_ENEMY | BIT_ENEMYWEAPON;
        createRectFixture(body, 0, 0, 14, 14, 0, 2000, f, e);

        e.add(engine.createComponent(BodyComponent.class).create(body));

        engine.addEntity(e);
    }

    public static void createHut(BuildingData data, int x, int y, GameMap gameMap) {
        Array<EntityData> fighterData=new Array<>();
        for(int i=0;i<10;i++){
            fighterData.add(new FighterData());
        }
        createMap(data,x,y,gameMap).add(engine.createComponent(SpawnComponent.class).create(fighterData));
    }


}
