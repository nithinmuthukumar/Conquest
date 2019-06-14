package com.nithinmuthukumar.conquest.Systems.UI;


import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Globals;
import com.nithinmuthukumar.conquest.Helpers.CClickListener;
import com.nithinmuthukumar.conquest.UIDatas.DataButton;
import com.nithinmuthukumar.conquest.UIDatas.ItemData;

import static com.nithinmuthukumar.conquest.Globals.equipComp;

public class Shop extends Group {
    private int shopSize = 5;
    private String[] items = new String[]{"katana", "dagger", "crossbow", "bomb", "dynamite", "poison", "knight shield", "ruby shield", "hero shield",
            "villager sword", "apprentice sword", "fighter sword", "short sword", "village bow"};
    private Table table;

    public Shop() {
        Label title = new Label("Shop", Assets.style);
        table = new Table();
        for (int y = 0; y < shopSize; y++) {
            for (int x = 0; x < shopSize; x++) {
                DataButton button;
                if (y * shopSize + x < items.length) {
                    button = new DataButton(Assets.itemDatas.get(items[y * shopSize + x]), "inventory", true);
                    button.addListener(new CClickListener<>(button.getData()) {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            if (Globals.player.getMoney() < object.getCost())
                                return;
                            Globals.player.spend(object.getCost());
                            equipComp.get(Globals.player.getEntity()).addToInventory((ItemData) object);
                            super.clicked(event, x, y);
                        }
                    });
                } else {
                    button = new DataButton("inventory", true);
                }
                table.add(button).size(32, 32);

            }
            table.row();
        }


        table.setPosition(200, 200);
        title.setPosition(100, 400);
        addActor(title);
        addActor(table);


    }
}
