package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class RemovalComponent implements Component, Pool.Poolable {

    public float countDown;

    public RemovalComponent create(float countDown) {
        this.countDown=countDown;
        return this;
    }

    @Override
    public void reset() {
        countDown=0;

    }


}
