package com.nithinmuthukumar.conquest.Components;

//identifies an entity that poisons when it collides
public class PoisonComponent implements BaseComponent {
    @Override
    public BaseComponent create() {
        return this;
    }

    @Override
    public void reset() {

    }
}
