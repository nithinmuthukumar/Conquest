package com.nithinmuthukumar.conquest.Components;

import com.badlogic.gdx.utils.*;
import com.nithinmuthukumar.conquest.Helpers.Assets;
import com.nithinmuthukumar.conquest.Helpers.Spawn;
import com.nithinmuthukumar.conquest.Recipe;

public class SpawnComponent implements BaseComponent {
    public Array<Recipe> spawnable;
    public Queue<Spawn> inLine;

    @Override
    public BaseComponent create(JsonValue args) {
        for(JsonValue sp:args.get(0)){
            spawnable.add(Assets.recipes.get(sp.asString()));
        }

        return this;
    }
    public SpawnComponent create(Array<Recipe> spawnable) {
        this.spawnable=spawnable;
        inLine=new Queue<>();
        return this;
    }

    @Override
    public void reset() {

    }


}
