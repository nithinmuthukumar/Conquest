package com.nithinmuthukumar.conquest;


import com.badlogic.ashley.core.Entity;

public class Player {


    private int money;
    private int wood;
    private int crystal;
    private int score;
    private Entity entity;

    public Player(Entity entity) {
        this.entity = entity;

    }

    public Entity getEntity() {
        return entity;
    }

    public int getMoney() {
        return money;
    }

    public int getWood() {
        return wood;
    }

    public int getCrystal() {
        return crystal;
    }

    public int getScore() {
        return score;
    }

}
