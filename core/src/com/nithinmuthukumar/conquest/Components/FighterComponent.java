package com.nithinmuthukumar.conquest.Components;

import com.badlogic.gdx.utils.JsonValue;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Recipe;

public class FighterComponent implements BaseComponent {
    public float range;
    private String key;
    public Recipe weapon;

    @Override
    public BaseComponent create() {
        weapon= Assets.recipes.get(key);
        return this;


    }

    public FighterComponent create(float range, String weapon) {
        this.range = range;
        this.weapon = Assets.recipes.get(weapon);
        return this;

    }

    @Override
    public void reset() {
        range=0;
        weapon=null;
    }


}
