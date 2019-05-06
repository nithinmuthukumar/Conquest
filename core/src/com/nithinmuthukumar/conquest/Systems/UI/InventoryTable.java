package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.nithinmuthukumar.conquest.Globals;
import com.nithinmuthukumar.conquest.UIDatas.DataButton;

import static com.nithinmuthukumar.conquest.Globals.equipComp;


public class InventoryTable extends Table {
    private Entity player = Globals.player.getEntity();

    private boolean full;

    public InventoryTable() {
        setDebug(true);
        setPosition(200, 200);


    }


    @Override
    public void act(float delta) {
        clear();


        for (int y = 0; y < 10; y++) {

            for (int x = 0; x < 10; x++) {

                if (equipComp.get(player).inventory[y][x] == null)
                    add().size(16, 16);
                else
                    add(new DataButton(equipComp.get(player).inventory[y][x])).size(32, 32);
            }
            row();

        }

        super.act(delta);

    }
}
