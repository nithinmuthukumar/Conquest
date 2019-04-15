package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class HealthComponent implements Component, Pool.Poolable {
    public int health;
    public int maxHealth;

    public HealthComponent create(int health){
        this.health=health;
        this.maxHealth=health;
        return this;
    }

    @Override
    public void reset() {
        health=0;

    }

    public void damage(int damage) {
        health-=damage;
    }
}
