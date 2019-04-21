package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.JsonValue;

public class EnemyComponent implements BaseComponent {


    @Override
    public BaseComponent create(JsonValue args) {
        return this;
    }

    @Override
    public void reset() {

    }
}
