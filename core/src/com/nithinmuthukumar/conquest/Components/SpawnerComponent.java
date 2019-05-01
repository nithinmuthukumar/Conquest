package com.nithinmuthukumar.conquest.Components;

import com.badlogic.gdx.utils.*;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Helpers.SpawnNode;
import com.nithinmuthukumar.conquest.Recipe;

public class SpawnerComponent implements BaseComponent {
    public String[] spawnableKeys;
    public Queue<SpawnNode> inLine;
    public ObjectSet<String> spawnable;

    @Override
    public BaseComponent create() {
        inLine=new Queue<>();
        spawnable=new ObjectSet<>();
        for(String s: spawnableKeys){
            spawnable.add(s);
        }



        return this;
    }


    @Override
    public void reset() {

    }


}
