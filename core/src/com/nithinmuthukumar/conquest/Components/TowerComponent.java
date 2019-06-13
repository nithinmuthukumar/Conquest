package com.nithinmuthukumar.conquest.Components;

//identifies a tower
public class TowerComponent implements BaseComponent {
    @Override
    public BaseComponent create() {
        return this;
    }

    @Override
    public void reset() {

    }
}
