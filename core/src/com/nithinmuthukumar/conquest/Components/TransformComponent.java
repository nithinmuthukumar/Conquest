package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class TransformComponent extends Vector2 implements Component, Pool.Poolable {
    public float width, height;
    public float z;
    public float rotation=0;

    public TransformComponent create(float x, float y, float z, float width, float height) {
        set(x,y);
        this.width = width;
        this.height = height;
        this.z = z;
        return this;

    }

    public float getRenderX() {
        return x - width / 2;


    }

    public float getRenderY() {
        return y - height / 2;

    }

    @Override
    public void reset() {
        set(0,0);
        width=0;
        height=0;
        z=0;

    }
}
