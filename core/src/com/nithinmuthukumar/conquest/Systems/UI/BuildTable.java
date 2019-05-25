package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nithinmuthukumar.conquest.Components.Identifiers.AllianceComponent;
import com.nithinmuthukumar.conquest.GameMap;
import com.nithinmuthukumar.conquest.Helpers.CClickListener;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;
import com.nithinmuthukumar.conquest.UIDatas.BuildingData;
import com.nithinmuthukumar.conquest.UIDatas.DataButton;

import static com.nithinmuthukumar.conquest.Assets.buildingDatas;
import static com.nithinmuthukumar.conquest.Conquest.engine;
import static com.nithinmuthukumar.conquest.Helpers.Utils.*;

public class BuildTable extends Table {
    private GameMap gameMap;


    private DataButton selected;
    private ClickListener touchUpListener = new ClickListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            super.touchUp(event, x, y, pointer, button);
            int buildX = snapToGrid(gameMap, screenToCameraX(Gdx.input.getX()) - selected.data.icon.getRegionWidth() / 2);
            int buildY = snapToGrid(gameMap, screenToCameraY(Gdx.input.getY()) - selected.data.icon.getRegionHeight() / 2);

            if (new Rectangle(getX(), getY(), getWidth(), getHeight()).contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                return;
            }
            if (gameMap.isPlaceable(((BuildingData) selected.data).tileLayer, x, y))
                EntityFactory.createBuilding(buildX, buildY, (BuildingData) selected.data, gameMap).add(engine.createComponent(AllianceComponent.class).create(0));


        }


    };

    public BuildTable(GameMap gameMap) {
        setDebug(true);

        this.gameMap = gameMap;
        setSize(Gdx.graphics.getWidth() / 2, 100);
        for (BuildingData bd : buildingDatas.values()) {
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


    public ClickListener getTouchUpListener() {
        return touchUpListener;
    }

    @Override
    public void act(float delta) {

        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        int checkX = snapToGrid(gameMap, screenToCameraX(Gdx.input.getX()) - selected.data.icon.getRegionWidth() / 2);
        int checkY = snapToGrid(gameMap, screenToCameraY(Gdx.input.getY()) - selected.data.icon.getRegionHeight() / 2);
        batch.setColor(gameMap.isPlaceable(((BuildingData) selected.data).tileLayer, checkX, checkY) ? Color.WHITE : Color.RED);
        batch.draw(selected.data.icon, snapToGrid(gameMap, Gdx.input.getX() - selected.data.icon.getRegionWidth() / 2), snapToGrid(gameMap, Gdx.graphics.getHeight() - Gdx.input.getY() - selected.data.icon.getRegionHeight() / 2));


        super.draw(batch, parentAlpha);
    }
}
