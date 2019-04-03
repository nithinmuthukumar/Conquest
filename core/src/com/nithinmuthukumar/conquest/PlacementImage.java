package com.nithinmuthukumar.conquest;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PlacementImage extends Image {
    private Map map;
    private BuildingData data;
    private boolean alive = true;

    public PlacementImage(BuildingData data, Map map) {
        super(data.icon);
        this.data = data;
        this.map = map;
        addListener(new ClickListener() {
            @Override
            public boolean isPressed() {
                alive = true;
                return super.isPressed();
            }
        });
    }

    @Override
    public void act(float delta) {
        System.out.println(alive);
        if (!alive) remove();
        else alive = false;
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color c = batch.getColor();
        batch.setColor(map.isPlaceable(data.tileLayer, getX(), getY()) ? Color.RED : Color.WHITE);
        super.draw(batch, parentAlpha);
        batch.setColor(c);
    }
}
