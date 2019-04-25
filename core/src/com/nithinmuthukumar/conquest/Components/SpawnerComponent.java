package com.nithinmuthukumar.conquest.Components;

import com.badlogic.gdx.utils.*;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Helpers.SpawnNode;
import com.nithinmuthukumar.conquest.Recipe;

public class SpawnerComponent implements BaseComponent {
    public Array<Recipe> spawnable;
    public Queue<SpawnNode> inLine;
    private String[] spawnableKeys;

    @Override
    public BaseComponent create() {
        inLine=new Queue<>();
        spawnable=new Array<>();
        for(String sp:spawnableKeys){
            spawnable.add(Assets.recipes.get(sp));
        }

        return this;
    }
    public SpawnerComponent create(Array<Recipe> spawnable) {
        this.spawnable=spawnable;
        inLine=new Queue<>();
        return this;
    }

    @Override
    public void reset() {

    }


}
