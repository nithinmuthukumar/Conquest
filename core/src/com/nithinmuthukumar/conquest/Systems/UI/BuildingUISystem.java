package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Components.Identifiers.InvisibleComponent;
import com.nithinmuthukumar.conquest.Components.UIComponents.BuildingComponent;
import com.nithinmuthukumar.conquest.GameMap;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Helpers.CClickListener;

import static com.nithinmuthukumar.conquest.Globals.*;
import static com.nithinmuthukumar.conquest.Globals.camera;
import static com.nithinmuthukumar.conquest.Helpers.Utils.*;

public class BuildingUISystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private GameMap gameMap;
    private Table table;
    private Entity selected=null;
    private float mouseX, mouseY;
    private Listener<int[]> mouseMovedListener = (Signal<int[]> signal, int[] object) -> {
        mouseX = object[0];
        mouseY = object[1];
    };
    private Listener<int[]> touchUpListener = (Signal<int[]> signal, int[] object) -> {
        int x = snapToGrid(gameMap, mouseX + camera.position.x - Gdx.graphics.getWidth() / 2);
        int y = snapToGrid(gameMap, Gdx.graphics.getHeight() / 2 - mouseY + camera.position.y);
        if (gameMap.isPlaceable(buildingComp.get(selected), x, y))
            createBuilding(buildingComp.get(selected), x, y, gameMap);

    };


    public BuildingUISystem(GameMap gameMap) {
        this.gameMap=gameMap;

        table=new Table(){
            @Override
            public void draw(Batch batch, float parentAlpha) {
                super.draw(batch, parentAlpha);
            }

            @Override
            public void setVisible(boolean visible) {
                PooledEngine e=(PooledEngine)getEngine();
                if (visible) {
                    inputHandler.addListener("touchUp", touchUpListener);
                    inputHandler.addListener("mouseMoved", mouseMovedListener);
                } else {
                    inputHandler.removeListener("touchUp", touchUpListener);
                    inputHandler.removeListener("mouseMoved", mouseMovedListener);
                }

                //selected.add(e.createComponent(InvisibleComponent.class));
                super.setVisible(visible);
            }
        };

        table.setPosition(200,200);




    }

    @Override
    public void addedToEngine(Engine engine) {
        entities=engine.getEntitiesFor(Family.all(BuildingComponent.class).get());
        setSelected(entities.first());
        for(int i=0;i<entities.size();i++){
            Image img=new Image(buildingComp.get(entities.get(i)).image);
            table.add(img);
            img.addListener(new CClickListener<>(entities.get(i)){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    setSelected(object);

                    super.clicked(event, x, y);
                }
            });


        }
        table.setVisible(false);

        super.addedToEngine(engine);
    }
    private void setSelected(Entity entity){
        if(selected!=null){
            selected.remove(RenderableComponent.class);
            selected.remove(TransformComponent.class);

        }
        selected=entity;
        BuildingComponent building=buildingComp.get(selected);
        selected.add(engine.createComponent(RenderableComponent.class).create(building.image));
        selected.add(engine.createComponent(TransformComponent.class)
                .create(screenToCameraX(mouseX), screenToCameraY(mouseY), 3, building.image.getWidth(),building.image.getHeight()));
        System.out.println(true);


    }

    @Override
    public void update(float deltaTime) {
        float checkX = snapToGrid(gameMap, screenToCameraX(mouseX)- Gdx.graphics.getWidth() / 2);
        float checkY = snapToGrid(gameMap, screenToCameraY(mouseY));
        renderComp.get(selected).color.set(gameMap.isPlaceable(buildingComp.get(selected), checkX, checkY) ? Color.WHITE : Color.RED);
        transformComp.get(selected).set(checkX, checkY);




        super.update(deltaTime);
    }
    //x and y must be bottom left coordinates of the image
    public static Entity createBuilding(BuildingComponent data, int x, int y, GameMap gameMap) {
        int mapX = MathUtils.round(x - data.image.getWidth() / 2);
        int mapY = MathUtils.round(y - data.image.getHeight() / 2);
        Entity e = Assets.recipes.get(data.name).make();
        e.add(engine.createComponent(RenderableComponent.class).create(data.image));
        e.add(engine.createComponent(TransformComponent.class).create(x, y, 0, data.image.getWidth(), data.image.getHeight()));
        gameMap.addLayer(data.tileLayer, mapX, mapY, data.image, 0);
        BodyComponent body = engine.createComponent(BodyComponent.class).create(x, y, BodyDef.BodyType.StaticBody);
        for(RectangleMapObject object: data.collisionLayer){
            Rectangle rect=object.getRectangle();
            Filter f=new Filter();
            f.categoryBits= -1;
            f.maskBits= -1;
            if(object.getProperties().containsKey("collideinfo"))
                f.groupIndex=(short)object.getProperties().get("collideinfo",Integer.class).intValue();

            createRectFixture(body.body, rect.x- data.image.getWidth() / 2+rect.width/2,
                    rect.y- data.image.getHeight() / 2+rect.height/2, rect.width/2, rect.height/2, 0, 0, f, e);
        }
        e.add(body);


        engine.addEntity(e);
        return e;

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
    public Table getUI(){
        return table;

    }
}
