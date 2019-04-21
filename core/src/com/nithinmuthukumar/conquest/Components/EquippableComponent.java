package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;

public class EquippableComponent implements BaseComponent {
    @Override
    public BaseComponent create(JsonValue args) {
        return this;
    }

    @Override
    public void reset() {

    }
}
