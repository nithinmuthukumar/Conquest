package com.nithinmuthukumar.conquest;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class BuildingImage extends ImageButton {
    public BuildingImage(BuildingData data, Map map) {
        super(new TextureRegionDrawable(data.icon));
        addListener(new ClickListener() {
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                getParent().addActor(new PlacementImage(data, map));
                super.touchDragged(event, x, y, pointer);
            }
        });
    }

}
