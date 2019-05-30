package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.Helpers.CClickListener;
import com.nithinmuthukumar.conquest.Server.BuildMessage;
import com.nithinmuthukumar.conquest.UIDatas.BuildingData;
import com.nithinmuthukumar.conquest.UIDatas.DataButton;

import static com.nithinmuthukumar.conquest.Assets.buildingDatas;
import static com.nithinmuthukumar.conquest.Conquest.gameMap;
import static com.nithinmuthukumar.conquest.Helpers.Utils.*;

public class BuildTable extends Table {


    private DataButton selected;
    private ClickListener touchUpListener = new ClickListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            super.touchUp(event, x, y, pointer, button);
            int buildX = snapToGrid(gameMap, screenToCameraX(Gdx.input.getX()) - selected.getData().icon.getRegionWidth() / 2);
            int buildY = snapToGrid(gameMap, screenToCameraY(Gdx.input.getY()) - selected.getData().icon.getRegionHeight() / 2);

            if (new Rectangle(getX(), getY(), getWidth(), getHeight()).contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                return;
            }
            if (gameMap.isPlaceable(((BuildingData) selected.getData()).tileLayer, buildX, buildY)) {
                System.out.println(false);
                Conquest.client.getClient().sendTCP(new BuildMessage(Conquest.client.getClient().getID(), buildX, buildY, selected.getData().name));
            }



        }


    };

    public BuildTable() {
        setDebug(true);
        setSize(Gdx.graphics.getWidth() / 2, 100);
        for (BuildingData bd : buildingDatas.values()) {
            DataButton btn = new DataButton(bd);
            btn.setSize(50, 50);


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


        int buildX = snapToGrid(gameMap, screenToCameraX(Gdx.input.getX()) - selected.getData().icon.getRegionWidth() / 2);
        int buildY = snapToGrid(gameMap, screenToCameraY(Gdx.input.getY()) - selected.getData().icon.getRegionHeight() / 2);
        batch.setColor(gameMap.isPlaceable(((BuildingData) selected.getData()).tileLayer, buildX, buildY) ? Color.WHITE : Color.RED);
        batch.draw(selected.getData().icon, snapToGrid(gameMap, Gdx.input.getX() - selected.getData().icon.getRegionWidth() / 2), snapToGrid(gameMap, Gdx.graphics.getHeight() - Gdx.input.getY() - selected.getData().icon.getRegionHeight() / 2));


        super.draw(batch, parentAlpha);
    }

    @Override
    protected void setStage(Stage stage) {
        super.setStage(stage);
    }
}
