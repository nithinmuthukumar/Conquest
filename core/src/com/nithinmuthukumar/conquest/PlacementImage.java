package com.nithinmuthukumar.conquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static com.nithinmuthukumar.conquest.Utils.print;


public class PlacementImage extends Image {
    private GameMap gameMap;
    private BuildingData data;
    private boolean alive = true;

    public PlacementImage(BuildingData data, GameMap gameMap) {

        super(data.image);
        this.data = data;
        this.gameMap = gameMap;
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if (gameMap.isPlaceable(data.tileLayer, getX(), getY())) {

                    EntityFactory.createMap(data, (MathUtils.round(getX())), (MathUtils.round(getY())), gameMap);
                } else {

                }
            }
        });
    }

    @Override
    public void act(float delta) {


        //if (!alive) remove();
        //else alive = false;
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (alive) setPosition(Gdx.input.getX() - 10, Gdx.graphics.getHeight() - Gdx.input.getY() - 10);
        print("PlacementImage", Boolean.toString(alive));
        super.draw(batch, parentAlpha);
    }
}
