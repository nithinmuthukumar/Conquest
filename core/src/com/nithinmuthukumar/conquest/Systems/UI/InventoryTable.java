package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.nithinmuthukumar.conquest.UIDatas.DataButton;
import com.nithinmuthukumar.conquest.UIDatas.ItemData;

import static com.nithinmuthukumar.conquest.Conquest.player;
import static com.nithinmuthukumar.conquest.Globals.equipComp;

public class InventoryTable extends Table {


    private boolean full;
    private int curX;
    private int curY;
    private DataButton meleeSlot;
    private DataButton shootSlot;
    private DataButton throwSlot;
    private DataButton[][] inventory;


    public InventoryTable() {
        setDebug(true);
        setPosition(200, 200);
        inventory = new DataButton[5][5];
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                inventory[y][x] = new DataButton();
                add(inventory[y][x]).size(32, 32);
            }
            row();
        }


    }

    public void updateInventory() {
        ItemData[][] pInventory = equipComp.get(player.getEntity()).inventory;


        for (int y = 0; y < inventory.length; y++) {

            for (int x = 0; x < inventory[y].length; x++) {

                if (pInventory[y][x] != null)
                    inventory[y][x].setData(pInventory[y][x]);

                if (x == curX && y == curY) {
                    inventory[y][x].setDisabled(true);

                } else {
                    inventory[y][x].setDisabled(false);
                }

            }


        }
    }


    @Override
    public void act(float delta) {
        updateInventory();



        super.act(delta);

    }
}
