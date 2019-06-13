package com.nithinmuthukumar.conquest.Systems.UI;


import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Globals;
import com.nithinmuthukumar.conquest.Helpers.CClickListener;
import com.nithinmuthukumar.conquest.UIDatas.DataButton;
import com.nithinmuthukumar.conquest.UIDatas.ItemData;

import static com.nithinmuthukumar.conquest.Globals.equipComp;

public class Shop extends Group {
    private String[] items = new String[]{"katana", "dagger", "crossbow", "bomb", "dynamite", "poison"};
    private DataButton[][] onSale = new DataButton[5][5];

    public Shop() {
        for (int i = 0; i < items.length; i++) {
            DataButton button = new DataButton(Assets.itemDatas.get(items[i]), "inventory");

            button.addListener(new CClickListener<>(button.getData()) {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (Globals.player.getMoney() < object.cost)
                        return;
                    Globals.player.spend(object.cost);
                    equipComp.get(Globals.player.getEntity()).addToInventory((ItemData) object);
                    super.clicked(event, x, y);
                }
            });
            button.setPosition(200 + 32 * (i % 5), 200 + 32 * (i / 5));
            onSale[i / 5][i % 5] = button;
            addActor(button);


        }


    }
}
