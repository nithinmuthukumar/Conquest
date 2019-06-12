package com.nithinmuthukumar.conquest.Components;

import com.badlogic.gdx.utils.Array;
import com.nithinmuthukumar.conquest.UIDatas.ItemData;

public class EquipComponent implements BaseComponent {
    public Array<ItemData> inventory;
    public boolean equipping;

    @Override
    public BaseComponent create() {
        inventory = new Array<>();
        equipping = false;

        return this;
    }

    public void addToInventory(ItemData data) {
        inventory.add(data);


    }

    @Override
    public void reset() {

    }
}
