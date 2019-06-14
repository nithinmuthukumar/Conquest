package com.nithinmuthukumar.conquest.Components;

//identifies a bomb
public class ExplodeComponent implements BaseComponent {
    @Override
    public BaseComponent create() {
        return this;
    }

    @Override
    public void reset() {

    }
}
