package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class TargetComponent implements Component , Pool.Poolable {
    public Vector2 target;

    public TargetComponent create(Vector2 target) {
        this.target = target;
        return this;

    }

    @Override
    public void reset() {
        target=null;
    }
}
