package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
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
import com.nithinmuthukumar.conquest.Components.Identifiers.BuiltComponent;
import com.nithinmuthukumar.conquest.GameMap;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Helpers.CClickListener;
import com.nithinmuthukumar.conquest.UIDatas.BuildingData;

import static com.nithinmuthukumar.conquest.Assets.buildingDatas;
import static com.nithinmuthukumar.conquest.Globals.*;
import static com.nithinmuthukumar.conquest.Helpers.Utils.*;

public class BuildSystem extends EntitySystem {

    private GameMap gameMap;
    private Table table;
    private Entity selected=engine.createEntity();
    private BuildingData curData;
    private Listener<int[]> touchUpListener = (Signal<int[]> signal, int[] object) -> {
        int x = snapToGrid(gameMap, screenToCameraX(Gdx.input.getX()-curData.image.getWidth() / 2));
        int y = snapToGrid(gameMap, screenToCameraY(Gdx.input.getY()-curData.image.getHeight() / 2));
        if (gameMap.isPlaceable(curData, x, y))
            createBuilding( x, y, gameMap);

    };


    public BuildSystem(GameMap gameMap) {
        this.gameMap=gameMap;

        table=new Table(){
            @Override
            public void draw(Batch batch, float parentAlpha) {

                super.draw(batch, parentAlpha);
            }

            @Override
            public void setVisible(boolean visible) {
                if (visible) {
                    inputHandler.addListener("touchUp", touchUpListener);
                    selected.add(engine.createComponent(RenderableComponent.class).create(curData.image));
                    selected.add(engine.createComponent(TransformComponent.class).create(snapToGrid(gameMap,screenToCameraX(Gdx.input.getX()))-curData.image.getWidth()/2,snapToGrid(gameMap,screenToCameraY(Gdx.input.getY())),5,0,0));

                    setProcessing(true);
                } else {
                    inputHandler.removeListener("touchUp", touchUpListener);
                    selected.remove(RenderableComponent.class);
                    selected.remove(TransformComponent.class);


                    setProcessing(false);

                }


                super.setVisible(visible);
            }
        };

        table.setPosition(200,50);
        table.setSize(100,100);




    }

    @Override
    public void addedToEngine(Engine engine) {
        table.debug();
        curData=buildingDatas.first();
        engine.addEntity(selected);



        for(BuildingData bd:buildingDatas){
            Image img=new Image(bd.image);
            img.setSize(25,25);
            img.setScale(0.6f);

            table.add(img);
            img.addListener(new CClickListener<>(bd){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    curData=object;



                    super.clicked(event, x, y);
                }
            });


        }

        table.setVisible(false);

        super.addedToEngine(engine);
    }


    @Override
    public void update(float deltaTime) {

        float checkX = snapToGrid(gameMap, screenToCameraX(Gdx.input.getX()));
        float checkY = snapToGrid(gameMap, screenToCameraY(Gdx.input.getY()));
        renderComp.get(selected).region.setRegion(curData.image);
        renderComp.get(selected).color.set(gameMap.isPlaceable(curData, checkX, checkY) ? Color.WHITE : Color.RED);
        transformComp.get(selected).set(checkX, checkY);





        super.update(deltaTime);
    }
    //x and y must be bottom left coordinates of the image
    public  void createBuilding(int x, int y, GameMap gameMap) {
        int mapX = snapToGrid(gameMap,MathUtils.round(x - curData.image.getWidth() / 2));
        int mapY = snapToGrid(gameMap,MathUtils.round(y - curData.image.getHeight() / 2));
        Entity e;
        if(Assets.recipes.containsKey(curData.name))
            e = Assets.recipes.get(curData.name).make();
        else e=engine.createEntity();
        e.add(engine.createComponent(RenderableComponent.class).create(curData.image));
        e.add(engine.createComponent(TransformComponent.class).create(x, y, 0, curData.image.getWidth(), curData.image.getHeight()));
        e.add(engine.createComponent(BuiltComponent.class));
        gameMap.addLayer(curData.tileLayer, mapX, mapY, curData.image, 0);
        BodyComponent body = engine.createComponent(BodyComponent.class).create(x, y, BodyDef.BodyType.StaticBody);
        for(RectangleMapObject object: curData.collisionLayer){
            Rectangle rect=object.getRectangle();
            Filter f=new Filter();
            f.categoryBits= -1;
            f.maskBits= -1;
            if(object.getProperties().containsKey("collideinfo"))
                f.groupIndex=(short)object.getProperties().get("collideinfo",Integer.class).intValue();

            createRectFixture(body.body, rect.x- curData.image.getWidth() / 2+rect.width/2,
                    rect.y- curData.image.getHeight() / 2+rect.height/2, rect.width/2, rect.height/2, 0, 0, f, e);
        }
        e.add(body);



        engine.addEntity(e);


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
