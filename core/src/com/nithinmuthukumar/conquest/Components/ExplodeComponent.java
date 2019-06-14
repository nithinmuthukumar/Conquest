package com.nithinmuthukumar.conquest.Components;

//identifies an entity that explodes
public class ExplodeComponent implements BaseComponent {
    @Override
    public BaseComponent create() {
        return this;
    }

    @Override
    public void reset() {

    }
}
