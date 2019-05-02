package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.nithinmuthukumar.conquest.Components.Identifiers.AllyComponent;
import com.nithinmuthukumar.conquest.GameMap;
import com.nithinmuthukumar.conquest.Helpers.CClickListener;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;
import com.nithinmuthukumar.conquest.UIDatas.BuildingData;
import com.nithinmuthukumar.conquest.UIDatas.DataButton;

import static com.nithinmuthukumar.conquest.Assets.buildingDatas;
import static com.nithinmuthukumar.conquest.Globals.engine;
import static com.nithinmuthukumar.conquest.Globals.inputHandler;
import static com.nithinmuthukumar.conquest.Helpers.Utils.*;

public class BuildTable extends Table {
    private GameMap gameMap;

    private DataButton selected;
    private Listener<int[]> touchUpListener = (Signal<int[]> signal, int[] object) -> {
        int x = snapToGrid(gameMap, screenToCameraX(Gdx.input.getX()) - selected.data.icon.getWidth() / 2);
        int y = snapToGrid(gameMap, screenToCameraY(Gdx.input.getY()) - selected.data.icon.getHeight() / 2);

        if (new Rectangle(getX(), getY(), getWidth(), getHeight()).contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
            return;
        }
        if (gameMap.isPlaceable((BuildingData) selected.data, x, y))
            EntityFactory.createBuilding(x, y, (BuildingData) selected.data, gameMap).add(engine.createComponent(AllyComponent.class));

    };

    public BuildTable(GameMap gameMap) {
        setDebug(true);
        this.gameMap = gameMap;
        setSize(Gdx.graphics.getWidth() / 2, 100);
        for (BuildingData bd : buildingDatas) {
            DataButton btn = new DataButton(bd);


            add(btn).size(50, 50);
            btn.addListener(new CClickListener<>(bd) {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selected = new DataButton(object);

                    super.clicked(event, x, y);
                }
            });


        }
        selected = (DataButton) getChildren().first();
        setPosition(Gdx.graphics.getWidth() / 4, 0);


    }


    @Override
    protected void setParent(Group parent) {
        if (parent != null) {
            inputHandler.addListener("touchUp", touchUpListener);
        } else {
            inputHandler.removeListener("touchUp", touchUpListener);

        }
        super.setParent(parent);
    }

    @Override
    public void act(float delta) {

        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        int checkX = snapToGrid(gameMap, screenToCameraX(Gdx.input.getX()) - selected.data.icon.getWidth() / 2);
        int checkY = snapToGrid(gameMap, screenToCameraY(Gdx.input.getY()) - selected.data.icon.getHeight() / 2);
        batch.setColor(gameMap.isPlaceable((BuildingData) selected.data, checkX, checkY) ? Color.WHITE : Color.RED);
        batch.draw(selected.data.icon, snapToGrid(gameMap, Gdx.input.getX() - selected.data.icon.getWidth() / 2), snapToGrid(gameMap, Gdx.graphics.getHeight() - Gdx.input.getY() - selected.data.icon.getHeight() / 2));


        super.draw(batch, parentAlpha);
    }
}
