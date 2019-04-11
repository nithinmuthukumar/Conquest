package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class TargetComponent implements Component {
    public Vector2 target;

    public TargetComponent(Vector2 target) {
        this.target = target;

    }
}
