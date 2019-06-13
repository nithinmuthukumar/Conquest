package com.nithinmuthukumar.conquest.Components;

import com.badlogic.gdx.math.Vector2;

public class TargetComponent implements BaseComponent {
    //the current target of the entity
    public Vector2 target;
    @Override
    public BaseComponent create() {

        return this;
    }


    public TargetComponent create(Vector2 target) {
        this.target = target;
        return this;
    }


    @Override
    public void reset() {
        target=null;
    }


}
