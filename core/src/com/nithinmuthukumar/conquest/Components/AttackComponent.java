package com.nithinmuthukumar.conquest.Components;

import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Recipe;

public class AttackComponent implements BaseComponent {
    public float range;
    private String key;
    public Recipe weapon;
    public float coolDown;
    public float timer;
    public int sightDistance;

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
        sightDistance = 0;
    }


}
