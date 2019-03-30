package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;

public class TransformComponent extends Vector3 implements Component {
    private float width, height;

    public TransformComponent(float x, float y, float z, float width, float height) {
        super(x,y,z);
        this.width = width;
        this.height = height;

    }

    public float getRenderX() {
        return x - width / 2;


    }

    public float getRenderY() {
        return y - height / 2;

    }

}
