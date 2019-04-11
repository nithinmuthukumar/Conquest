package com.nithinmuthukumar.conquest.UIs;


import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.nithinmuthukumar.conquest.GameMap;
import com.nithinmuthukumar.conquest.Helpers.Assets;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;
import com.nithinmuthukumar.conquest.Utils;

import static com.nithinmuthukumar.conquest.Helpers.Globals.camera;
import static com.nithinmuthukumar.conquest.Helpers.Globals.inputHandler;

public class BuildingUI extends HorizontalGroup {
    private BuildingData selected;
    private float mouseX, mouseY;
    private float timer = 0;
    private GameMap gameMap;
    private Listener<int[]> mouseMovedListener = (Signal<int[]> signal, int[] object) -> {
        mouseX = object[0];
        mouseY = object[1];
        timer = 0;
    };
    private Listener<int[]> touchUpListener = (Signal<int[]> signal, int[] object) -> {
        int x = Utils.snapToGrid(gameMap, mouseX + camera.position.x - Gdx.graphics.getWidth() / 2);
        int y = Utils.snapToGrid(gameMap, Gdx.graphics.getHeight() / 2 - mouseY + camera.position.y);
        if (gameMap.isPlaceable(selected.tileLayer, x + selected.image.getWidth() / 2, y + selected.image.getHeight() / 2))
            EntityFactory.createMap(selected, x, y, gameMap);

    };


    public BuildingUI(GameMap gameMap) {
        debug();
        super.setVisible(false);
        selected = Assets.buildingDatas.first();
        this.gameMap = gameMap;
        for (BuildingData buildingData : Assets.buildingDatas) {
            Image buildingImage = new Image(buildingData.image);

            addActor(buildingImage);
            buildingImage.addListener(new CClickListener<>(buildingData) {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selected = object;

                    super.clicked(event, x, y);
                }
            });
        }

        setPosition(400, 200);
        //pane.setScrollbarsVisible(true);


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);
        float checkX = Utils.snapToGrid(gameMap, mouseX - selected.image.getWidth() / 2 + camera.position.x - Gdx.graphics.getWidth() / 2);
        float checkY = Utils.snapToGrid(gameMap, Gdx.graphics.getHeight() / 2 - mouseY + camera.position.y - selected.image.getHeight() / 2);
        batch.setColor(gameMap.isPlaceable(selected.tileLayer, checkX, checkY) ? Color.WHITE : Color.RED);
        batch.draw(selected.image, Utils.snapToGrid(gameMap, mouseX - selected.image.getWidth() / 2), Utils.snapToGrid(gameMap, Gdx.graphics.getHeight() - mouseY - selected.image.getHeight() / 2));
        batch.setColor(Color.WHITE);

    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            inputHandler.addListener("touchUp", touchUpListener);
            inputHandler.addListener("mouseMoved", mouseMovedListener);
        } else {
            inputHandler.removeListener("touchUp", touchUpListener);
            inputHandler.removeListener("mouseMoved", mouseMovedListener);
        }
        super.setVisible(visible);
    }
}
