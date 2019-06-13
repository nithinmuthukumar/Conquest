package com.nithinmuthukumar.conquest.Components;

//identifies a shooter
public class ShooterComponent implements BaseComponent {
    @Override
    public BaseComponent create() {
        return this;
    }

    @Override
    public void reset() {

    }
}
