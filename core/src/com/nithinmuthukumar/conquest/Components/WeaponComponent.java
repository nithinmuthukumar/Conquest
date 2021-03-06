package com.nithinmuthukumar.conquest.Components;

//identifies an entity that can do damage and how much damage it does
public class WeaponComponent implements BaseComponent {
    public float damage;
    @Override
    public BaseComponent create() {
        return this;
    }

    public WeaponComponent create(float damage) {
        this.damage=damage;
        return this;
    }
    @Override
    public void reset() {
        damage=0;
    }
}
