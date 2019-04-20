package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;
import com.nithinmuthukumar.conquest.Enums.Action;

public class ParticleComponent implements Component, Pool.Poolable {
    private ParticleEffectPool.PooledEffect defaultEffect;
    private ObjectMap<Action, ParticleEffectPool.PooledEffect> effectMap;
    public float timeTilDeath = 0.5f; // add a 1 second delay
    public Action action=null;

                                   //need to implement this as a map with Actions but how
                                                                    //ParticleEffectPool.PooledEffect... effects
    public ParticleComponent create(ParticleEffectPool.PooledEffect defaultEffect,ObjectMap<Action, ParticleEffectPool.PooledEffect> effects){

        this.defaultEffect=defaultEffect;
        this.effectMap=effects;

        return this;
    }

    public ParticleEffectPool.PooledEffect get() {
        if(action!=null||!effectMap.containsKey(action)){
            return effectMap.get(action);
        }
        else{
            return defaultEffect;
        }
    }

    @Override
    public void reset() {
    }
}
