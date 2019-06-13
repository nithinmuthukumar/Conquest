package com.nithinmuthukumar.conquest.Components;

public class HealthComponent implements BaseComponent {
    public int health;
    public int maxHealth;

    @Override
    public BaseComponent create() {
        this.maxHealth = health;

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
        System.out.println(damage);
        health-=damage;
    }


}
