package com.nithinmuthukumar.conquest.Components;

//identifies an entity which should be removed as soon as it collides
public class CollisionRemoveComponent implements BaseComponent {
    @Override
    public BaseComponent create() {
        return this;
    }

    @Override
    public void reset() {

    }
}
