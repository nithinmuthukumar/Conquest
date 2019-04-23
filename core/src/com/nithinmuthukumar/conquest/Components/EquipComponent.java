package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

public class EquipComponent implements BaseComponent {
    public ArrayList<Entity> equipped;

    @Override
    public BaseComponent create() {
        return this;
    }

    @Override
    public void reset() {

    }
}
