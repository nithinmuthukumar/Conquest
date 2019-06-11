package com.nithinmuthukumar.conquest.Components;

public class MeleeComponent implements BaseComponent {
    public int weaponOffset;
    @Override
    public BaseComponent create() {
        return this;
    }

    @Override
    public void reset() {
        weaponOffset = 0;
    }
}
