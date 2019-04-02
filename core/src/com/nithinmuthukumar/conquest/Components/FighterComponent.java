package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class FighterComponent implements Component {
    public float range;
    public Entity weapon;

    public FighterComponent(float range, Entity weapon) {
        this.range = range;
        this.weapon = weapon;

    }
}
