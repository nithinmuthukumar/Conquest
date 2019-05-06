package com.nithinmuthukumar.conquest;


import com.badlogic.ashley.core.Entity;

public class Player {
    private int money;
    private String id;
    private int score;
    private Entity entity;

    public Player(Entity entity) {
        this.entity = entity;

    }

    public Entity getEntity() {
        return entity;
    }

}
