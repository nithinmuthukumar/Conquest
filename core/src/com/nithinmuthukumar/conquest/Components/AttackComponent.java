package com.nithinmuthukumar.conquest.Components;

import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Recipe;

public class AttackComponent implements BaseComponent {
    //attacking range where entity is close enough to hit the target
    public float range;
    private String key;
    //the weapon used by the entity
    public Recipe weapon;
    //time between each attack
    public float coolDown;
    //time that has passes after the last attack
    public float timer;


    @Override
    public BaseComponent create() {
        weapon= Assets.recipes.get(key);
        timer=0;
        return this;


    }

    public AttackComponent create(float range, String weapon) {
        this.range = range;
        this.weapon = Assets.recipes.get(weapon);
        return this;

    }

    @Override
    public void reset() {
        range=0;
        weapon=null;
        key = null;
        coolDown = 0;
        timer = 0;
    }


}
