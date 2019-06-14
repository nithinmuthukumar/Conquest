package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nithinmuthukumar.conquest.Globals;
import com.nithinmuthukumar.conquest.Helpers.CClickListener;
import com.nithinmuthukumar.conquest.Server.BuildMessage;
import com.nithinmuthukumar.conquest.UIDatas.BuildingData;
import com.nithinmuthukumar.conquest.UIDatas.DataButton;

import static com.nithinmuthukumar.conquest.Assets.buildingDatas;
import static com.nithinmuthukumar.conquest.Globals.gameMap;
import static com.nithinmuthukumar.conquest.Globals.player;
import static com.nithinmuthukumar.conquest.Helpers.Utils.*;

public class BuildTable extends Table {

    //holds the current building being built
    private DataButton selected;
    private ClickListener listener = new ClickListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            super.touchUp(event, x, y, pointer, button);

            //finds the grid position of the building
            int buildX = snapToGrid(gameMap, screenToCameraX(Gdx.input.getX()) - selected.getData().getIcon().getRegionWidth() / 2);
            int buildY = snapToGrid(gameMap, screenToCameraY(Gdx.input.getY()) - selected.getData().getIcon().getRegionHeight() / 2);
            //checks if the clicking isn't within the building buttons in which case it exits
            if (new Rectangle(getX(), getY(), getWidth(), getHeight()).contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                return;
            }
            //makes sure the player has enough money and subtra
            if (selected.getData().getCost() <= player.getMoney()) {
                player.spend(selected.getData().getCost());
            } else {
                return;
            }
            //if the map is placeable a message is sent to the server
            if (gameMap.isPlaceable(((BuildingData) selected.getData()).getTileLayer(), buildX, buildY)) {
                Globals.conquestClient.getClient().sendTCP(new BuildMessage(selected.getData().getName(), buildX, buildY));
            }



        }


    };

    public BuildTable() {
        setDebug(true);
        setSize(Gdx.graphics.getWidth() / 2, 100);
        //creates a set of buttons with all the objects that can be placed
        for (BuildingData bd : buildingDatas.values()) {
            DataButton btn = new DataButton(bd, "building", true);
            btn.setSize(50, 50);


            add(btn).size(50, 50);
            //if the button is pressed selected is switched to the data of the btn
            btn.addListener(new CClickListener<>(bd) {
                @Override
                public void clicked(InputEvent event, float x, float y) {


                    selected = new DataButton(object, "default", false);



                    super.clicked(event, x, y);
                }
            });


        }

        selected = (DataButton) getChildren().first();
        setPosition(Gdx.graphics.getWidth() / 4, 0);


    }


    public ClickListener getListener() {
        return listener;
    }

    @Override
    public void act(float delta) {

        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        //checks to see if selected can be built at the current mouse position
        //the color of is tinted based on whether it can to red if not possible or normal if it is
        int buildX = snapToGrid(gameMap, screenToCameraX(Gdx.input.getX()) - selected.getData().getIcon().getRegionWidth() / 2);
        int buildY = snapToGrid(gameMap, screenToCameraY(Gdx.input.getY()) - selected.getData().getIcon().getRegionHeight() / 2);
        batch.setColor(gameMap.isPlaceable(((BuildingData) selected.getData()).getTileLayer(), buildX, buildY) ? Color.WHITE : Color.RED);
        batch.draw(selected.getData().getIcon(), snapToGrid(gameMap, Gdx.input.getX() - selected.getData().getIcon().getRegionWidth() / 2), snapToGrid(gameMap, Gdx.graphics.getHeight() - Gdx.input.getY() - selected.getData().getIcon().getRegionHeight() / 2));

        super.draw(batch, parentAlpha);
    }

    @Override
    protected void setStage(Stage stage) {
        super.setStage(stage);
    }
}
