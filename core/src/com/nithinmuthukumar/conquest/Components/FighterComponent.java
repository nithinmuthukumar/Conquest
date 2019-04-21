package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Pool;
import com.nithinmuthukumar.conquest.Helpers.Assets;
import com.nithinmuthukumar.conquest.Recipe;

public class FighterComponent implements BaseComponent {
    public float range;
    public Recipe weapon;

    @Override
    public BaseComponent create(JsonValue args) {
        range=args.getFloat("float");
        weapon= Assets.recipes.get(args.getString("weapon"));
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
