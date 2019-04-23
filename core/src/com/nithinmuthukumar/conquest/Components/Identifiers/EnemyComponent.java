package com.nithinmuthukumar.conquest.Components.Identifiers;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.JsonValue;
import com.nithinmuthukumar.conquest.Components.BaseComponent;

public class EnemyComponent implements BaseComponent {


    @Override
    public BaseComponent create() {
        return this;
    }

    @Override
    public void reset() {

    }
}
