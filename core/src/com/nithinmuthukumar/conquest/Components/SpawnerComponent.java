package com.nithinmuthukumar.conquest.Components;

import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.Queue;
import com.nithinmuthukumar.conquest.Helpers.SpawnNode;

//a spawner spawns other entities
public class SpawnerComponent implements BaseComponent {
    //all the spawnable entities
    public String[] spawnableKeys;
    //the current enitities being spawned
    public Queue<SpawnNode> inLine;
    //Why did I do this?
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
