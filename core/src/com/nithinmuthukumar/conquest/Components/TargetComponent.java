package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

import static com.nithinmuthukumar.conquest.Globals.transformComp;

public class TargetComponent implements BaseComponent {
    public Entity target;
    @Override
    public BaseComponent create() {
        return this;
    }

    public TargetComponent create(Entity entity) {
        this.target = entity;



        return this;

    }

    public Vector2 getPos() {
        if (target == null) {
            return null;
        }
        return transformComp.get(target);
    }


    @Override
    public void reset() {
        target=null;
    }


}
