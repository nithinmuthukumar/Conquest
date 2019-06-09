package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Conquest;

public class StatsTable extends Table {
    Label moneyLabel = new Label("0", Assets.style);
    Label woodLabel = new Label("0", Assets.style);
    Label crystalLabel = new Label("0", Assets.style);

    public StatsTable() {
        System.out.println(true);
        setDebug(true);

        add(new Image(new TextureRegionDrawable(Assets.style.get("Coin", TextureRegion.class))), moneyLabel);
        row();
        add(new Image(new TextureRegionDrawable(Assets.style.get("Wood A", TextureRegion.class))), woodLabel);
        row();
        add(crystalLabel);
        setPosition(25, 650);
        setSize(30, 40);


    }

    @Override
    public void act(float delta) {
        moneyLabel.setText(String.format(": %d", Conquest.player.getMoney()));
        woodLabel.setText(String.format(": %d", Conquest.player.getWood()));
        crystalLabel.setText(String.format(": %d", Conquest.player.getCrystal()));
        super.act(delta);
    }
}
