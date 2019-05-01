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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Components.Identifiers.BuiltComponent;
import com.nithinmuthukumar.conquest.GameMap;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Helpers.CClickListener;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;
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
        int x = snapToGrid(gameMap, screenToCameraX(Gdx.input.getX())-curData.image.getWidth()/2);
        int y = snapToGrid(gameMap, screenToCameraY(Gdx.input.getY())-curData.image.getHeight()/2);
        if (gameMap.isPlaceable(curData, x, y))
            EntityFactory.createBuilding( x, y,curData, gameMap);

    };


    public BuildSystem(GameMap gameMap) {
        this.gameMap=gameMap;


        table=new Table(){
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
        table.setSize(400,100);




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
        RenderableComponent renderable=renderComp.get(selected);
        int checkX = snapToGrid(gameMap, screenToCameraX(Gdx.input.getX())-curData.image.getWidth()/2);
        int checkY = snapToGrid(gameMap, screenToCameraY(Gdx.input.getY())-curData.image.getHeight()/2);
        renderable.region.setRegion(curData.image);
        renderable.color.set(gameMap.isPlaceable(curData, checkX, checkY) ? Color.WHITE : Color.RED);
        transformComp.get(selected).set(checkX, checkY);





        super.update(deltaTime);
    }


    public Table getUI(){
        return table;

    }
}
