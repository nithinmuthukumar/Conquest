package com.nithinmuthukumar.conquest.Components.Identifiers;

import com.badlogic.gdx.utils.JsonValue;
import com.nithinmuthukumar.conquest.Components.BaseComponent;

public class BuiltComponent implements BaseComponent {
    @Override
    public BaseComponent create() {
        return this;
    }

    @Override
    public void reset() {

    }
}
