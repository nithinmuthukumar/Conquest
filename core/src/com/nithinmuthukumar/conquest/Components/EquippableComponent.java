package com.nithinmuthukumar.conquest.Components;

import com.nithinmuthukumar.conquest.UIDatas.ItemData;

public class EquippableComponent implements BaseComponent {
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
