package com.nithinmuthukumar.conquest.Components;

//identifies an entity that attacks in close quarters
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
