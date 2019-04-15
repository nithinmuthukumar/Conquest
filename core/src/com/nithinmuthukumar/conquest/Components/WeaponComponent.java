package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class WeaponComponent implements Component, Pool.Poolable {
    public int damage;
    public WeaponComponent create(int damage){
        this.damage=damage;
        return this;

    }
    @Override
    public void reset() {
        damage=0;

    }
}
