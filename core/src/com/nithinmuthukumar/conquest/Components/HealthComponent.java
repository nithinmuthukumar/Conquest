package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Pool;

public class HealthComponent implements BaseComponent {
    public int health;
    public int maxHealth;

    @Override
    public BaseComponent create(JsonValue args) {
        health=args.getInt("health");
        maxHealth=args.getInt("maxHealth");

        return this;
    }

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
