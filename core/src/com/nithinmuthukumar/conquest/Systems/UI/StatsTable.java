package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Globals;
import com.nithinmuthukumar.conquest.UIDatas.Hearts;

public class StatsTable extends Group {
    Label moneyLabel = new Label("0", Assets.style);
    private Table table = new Table();
    public StatsTable() {


        table.add(new Hearts());
        table.row().size(20, 20);
        table.add(new Image(new TextureRegionDrawable(Assets.style.get("Coin", TextureRegion.class))), moneyLabel);
        table.setPosition(50, 650);
        //setSize(30, 40);
        Image bkgPics = new Image(Assets.style.getRegion("window5_145"));
        bkgPics.setPosition(50, 650);
        //bkgPics.setSize(200,16);
        addActor(bkgPics);
        addActor(table);





    }

    @Override
    public void act(float delta) {
        moneyLabel.setText(String.format(": %d", Globals.player.getMoney()));

        super.act(delta);
    }
}
