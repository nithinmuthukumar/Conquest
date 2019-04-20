package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.*;
import com.nithinmuthukumar.conquest.Datas.EntityData;
import com.nithinmuthukumar.conquest.Datas.FighterData;
import com.nithinmuthukumar.conquest.Helpers.Spawn;

public class SpawnComponent implements Component, Pool.Poolable {
    public Array<EntityData> spawnable;
    public Queue<Spawn> inLine;


    public SpawnComponent create(Array<EntityData> spawnable) {
        this.spawnable=spawnable;
        inLine=new Queue<>();
        return this;
    }

    @Override
    public void reset() {

    }
}
