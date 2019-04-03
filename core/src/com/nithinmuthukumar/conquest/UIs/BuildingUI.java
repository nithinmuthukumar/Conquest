package com.nithinmuthukumar.conquest.UIs;


import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.BuildingData;
import com.nithinmuthukumar.conquest.BuildingImage;
import com.nithinmuthukumar.conquest.Map;

public class BuildingUI {
    private ScrollPane pane;

    public BuildingUI(Stage stage, Map map) {
        HorizontalGroup group = new HorizontalGroup();

        for (BuildingData buildingData : Assets.buildingDatas) {
            BuildingImage buildingImage = new BuildingImage(buildingData, map);

            group.addActor(buildingImage);
        }

        pane = new ScrollPane(group, Assets.style);
        pane.setPosition(400, 0);
        //pane.setScrollbarsVisible(true);


    }

    public ScrollPane getPane() {
        return pane;
    }
}
