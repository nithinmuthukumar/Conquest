package com.nithinmuthukumar.conquest.Components;

import com.nithinmuthukumar.conquest.UIDatas.ItemData;

//an entity that can be picked up
public class EquippableComponent implements BaseComponent {
    //the data of the item it is
    public ItemData data;

    @Override
    public BaseComponent create() {
        return this;
    }

    public BaseComponent create(ItemData data) {
        this.data = data;
        return this;
    }

    @Override
    public void reset() {

    }
}
