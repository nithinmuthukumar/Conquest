// =-=-=-=-=-=-=-= CONQUEST =-=-=-=-=-=-=-=
//holds player info of the player on the conquestClient side of each instance

package com.nithinmuthukumar.conquest;


import com.badlogic.ashley.core.Entity;
import com.nithinmuthukumar.conquest.UIDatas.ItemData;

public class Player {

    private float score;
    private int money;
    private Entity entity;

    public Player(Entity entity) {
        score = 0;
        this.entity = entity;
        money = 1000;

    }

    public Entity getEntity() {
        return entity;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getMoney() {
        return money;
    }


    public void spend(int cost) {
        money -= cost;
    }

    public void take(ItemData data) {
        //the amount of money something is worth is its rarity times 100
        if (data.getType().equals("money")) {
            money += (data.getRarity() + 1) * 100;
        }
    }
}
