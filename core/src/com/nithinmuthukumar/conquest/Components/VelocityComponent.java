package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.nithinmuthukumar.conquest.Utils;


public class VelocityComponent extends Vector2 implements Component, Pool.Poolable {

    public VelocityComponent create(float magnitude){
        set(1,0);
        scl(magnitude);
        return this;


    }
    @Override
    public void reset() {
        set(0,0);
        setAngle(0);
    }
}
