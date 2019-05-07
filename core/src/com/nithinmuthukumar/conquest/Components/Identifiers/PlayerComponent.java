package com.nithinmuthukumar.conquest.Components.Identifiers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.nithinmuthukumar.conquest.Components.BaseComponent;

public class PlayerComponent implements BaseComponent {
    public Array<Entity> equipped;

    @Override
    public BaseComponent create() {
        equipped = new Array<>();


        return this;
    }

    @Override
    public void reset() {
        equipped = null;
    }
}
