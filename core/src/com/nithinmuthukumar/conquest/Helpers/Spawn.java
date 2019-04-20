package com.nithinmuthukumar.conquest.Helpers;

import com.badlogic.gdx.utils.Pool;
import com.nithinmuthukumar.conquest.Datas.EntityData;

public class Spawn{

    public float timer;
    public EntityData data;
    public Spawn(EntityData data,float timer){
        this.data=data;
        this.timer=timer;


    }

}
