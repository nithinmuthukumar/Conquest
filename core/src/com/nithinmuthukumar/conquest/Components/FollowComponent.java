package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Entity;

public class FollowComponent implements BaseComponent {
    public Entity target;

    public BaseComponent create(Entity target) {
        this.target = target;
        return this;
    }

    @Override
    public BaseComponent create() {
        return this;
    }

    @Override
    public void reset() {

    }
}
