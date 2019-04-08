package com.nithinmuthukumar.conquest;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import static com.nithinmuthukumar.conquest.Utils.print;


public class BuildingImage extends ImageButton {
    public BuildingImage(BuildingData data, GameMap gameMap) {
        super(new TextureRegionDrawable(data.image));
        addListener(new ClickListener() {
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                print("BuildingImage", "true");
                getStage().addActor(new PlacementImage(data, gameMap));
                super.touchDragged(event, x, y, pointer);
            }
        });
    }

}
