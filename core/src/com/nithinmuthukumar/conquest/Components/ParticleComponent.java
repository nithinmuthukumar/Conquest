package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Helpers.Assets;

public class ParticleComponent implements BaseComponent {
    private PooledEffect defaultEffect;
    private ObjectMap<Action, ParticleEffectPool.PooledEffect> effectMap;
    public Action action=null;

    @Override
    public BaseComponent create(JsonValue args) {
        defaultEffect= Assets.effectPools.get(args.getString("default")).obtain();
        for(JsonValue effects: args.get("effects")){
            effectMap.put(Action.valueOf(effects.name),Assets.effectPools.get(args.getString(effects.child.asString())).obtain());
        }
        return this;
    }
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
