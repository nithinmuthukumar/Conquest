package com.nithinmuthukumar.conquest.UIs;

import com.badlogic.ashley.signals.Listener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.BuildingData;

import static com.nithinmuthukumar.conquest.Utils.print;

public class BuildingUI extends ScrollPane {

    private Image map;
    private Listener<TextureRegionDrawable> imageListener = (signal, img) -> {
        map = new Image(img);
    };

    public BuildingUI(TextureRegionDrawable drawable) {
        super(null, Assets.style);
        map = new Image(drawable);
        map.setSize(400, 400);


        HorizontalGroup group = new HorizontalGroup();
        group.addActor(map);
        for(BuildingData buildingData:Assets.buildingDatas) {
            ImageButton btn = new ImageButton(new TextureRegionDrawable(buildingData.icon));
            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    print("BuildingUI","clicked");
                    super.clicked(event, x, y);
                }
            });
            group.addActor(btn);
        }

        setActor(group);
        setSize(500, 500);


    }

    public Listener getImageListener() {
        return imageListener;
    }
}
