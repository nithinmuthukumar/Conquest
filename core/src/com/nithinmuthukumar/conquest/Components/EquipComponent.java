package com.nithinmuthukumar.conquest.Components;

import com.nithinmuthukumar.conquest.UIDatas.ItemData;

public class EquipComponent implements BaseComponent {
    public ItemData[][] inventory;
    public boolean equipping;

    @Override
    public BaseComponent create() {
        inventory = new ItemData[10][10];
        equipping = false;

        return this;
    }

    public void addToInventory(ItemData data) {
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                if (inventory[y][x] == null) {
                    inventory[y][x] = data;
                    return;
                }

            }
        }


    }

    @Override
    public void reset() {

    }
}
