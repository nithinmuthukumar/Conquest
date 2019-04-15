package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

public class FighterComponent implements Component, Pool.Poolable {
    public float range;
    public Entity weapon;

    public FighterComponent create(float range, Entity weapon) {
        this.range = range;
        this.weapon = weapon;
        return this;

    }

    @Override
    public void reset() {
        range=0;
        weapon=null;
    }
}
