package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;

public class SpawningComponent implements Component, Pool.Poolable {
    public SpawningComponent create(){

        return this;

    }
    @Override
    public void reset() {

    }
}
