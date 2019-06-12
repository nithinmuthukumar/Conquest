package com.nithinmuthukumar.conquest;


import com.badlogic.ashley.core.Entity;
import com.nithinmuthukumar.conquest.UIDatas.ItemData;

public class Player {



    private int money;
    private int wood;
    private int crystal;
    private int score;
    private Entity entity;

    public Player(Entity entity) {
        this.entity = entity;
        money = 1000;
        wood = 0;
        crystal = 0;
        score = 0;

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

    public void spend(int cost) {
        money -= cost;
    }

    public void take(ItemData data) {
        System.out.println(data.getType());
        if (data.getType().equals("money")) {
            money += 100;
        } else if (data.getType().equals("crystal")) {
            crystal += 100;
        } else if (data.getType().equals("wood")) {
            wood += 100;
        }
    }
}
