package com.nithinmuthukumar.conquest.Components;

//determines the alliance of an entity
public class AllianceComponent implements BaseComponent {
    //id is who the entity is allied with
    public int side;

    @Override
    public BaseComponent create() {
        return this;
    }

    public BaseComponent create(int side) {
        this.side = side;
        return this;
    }

    @Override
    public void reset() {

    }
}
