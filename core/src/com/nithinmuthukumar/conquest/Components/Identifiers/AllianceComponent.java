package com.nithinmuthukumar.conquest.Components.Identifiers;

import com.nithinmuthukumar.conquest.Components.BaseComponent;

public class AllianceComponent implements BaseComponent {
    //side is who the entity is allied with
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
