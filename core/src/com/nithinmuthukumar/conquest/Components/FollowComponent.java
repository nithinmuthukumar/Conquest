package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Entity;

//class that holds information about a target
public class FollowComponent implements BaseComponent {
    //the entity they are chasing
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
