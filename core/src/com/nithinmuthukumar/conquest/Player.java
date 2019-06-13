package com.nithinmuthukumar.conquest;


import com.badlogic.ashley.core.Entity;
import com.nithinmuthukumar.conquest.UIDatas.ItemData;

//holds player info of the player on the client side of each instance
public class Player {



    private int money;
    private Entity entity;

    public Player(Entity entity) {
        this.entity = entity;
        money = 1000;

    }

    public Entity getEntity() {
        return entity;
    }

    public int getMoney() {
        return money;
    }


    public void spend(int cost) {
        money -= cost;
    }

    public void take(ItemData data) {
        if (data.getType() == "money") {
            money += data.getRarity() * 100;
        }
    }
}
