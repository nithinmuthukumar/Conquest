package com.nithinmuthukumar.conquest.UIs;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.BuildingData;

import java.util.ArrayList;

import static com.nithinmuthukumar.conquest.Utils.print;

public class BuildingUI{
    private ScrollPane pane;
    private Stage stage;

    public BuildingUI(Stage stage) {
        this.stage=stage;


        HorizontalGroup buttonGroup=new HorizontalGroup();
        for(BuildingData buildingData:Assets.buildingDatas) {
            ImageButton btn = new ImageButton(new TextureRegionDrawable(buildingData.icon));
            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    print("BuildingUI","clicked");
                    super.clicked(event, x, y);
                }
            });

            buttonGroup.addActor(btn);
        }

        pane=new ScrollPane(buttonGroup);
        pane.setSize(200,200);
        stage.addActor(pane);


    }
}
