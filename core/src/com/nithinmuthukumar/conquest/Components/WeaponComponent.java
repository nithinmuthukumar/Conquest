package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Pool;

public class WeaponComponent implements BaseComponent {
    public int damage;
    @Override
    public BaseComponent create(JsonValue args) {
        damage=args.getInt("damage");
        return this;
    }
    public WeaponComponent create(int damage){
        this.damage=damage;
        return this;

    }
    @Override
    public void reset() {
        damage=0;

    }


}
