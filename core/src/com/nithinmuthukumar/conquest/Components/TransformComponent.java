package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class TransformComponent extends Vector2 implements Component {
    private float width, height;
    public float z;

    public TransformComponent(float x, float y, float z, float width, float height) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.z = z;

    }

    public float getRenderX() {
        return x - width / 2;


    }

    public float getRenderY() {
        return y - height / 2;

    }

}
