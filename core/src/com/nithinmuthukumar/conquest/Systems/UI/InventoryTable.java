package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Globals;
import com.nithinmuthukumar.conquest.Helpers.CClickListener;
import com.nithinmuthukumar.conquest.UIDatas.DataButton;

import static com.nithinmuthukumar.conquest.Globals.equipComp;
import static com.nithinmuthukumar.conquest.Globals.playerComp;


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
                    add().size(32, 32);
                else {
                    playerComp.get(player).equipped[0] = Assets.recipes.get(equipComp.get(player).inventory[y][x].name);
                    DataButton button = new DataButton(equipComp.get(player).inventory[y][x]);
                    button.addListener(new CClickListener<>(button) {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {

                            playerComp.get(player).equipped[0] = Assets.recipes.get(object);
                        }
                    });

                    add(button).size(32, 32);
                }
            }

            row();

        }

        super.act(delta);

    }
}
