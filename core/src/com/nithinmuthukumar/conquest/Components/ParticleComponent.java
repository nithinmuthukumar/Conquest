package com.nithinmuthukumar.conquest.Components;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.utils.ObjectMap;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Enums.Action;

public class ParticleComponent implements BaseComponent {
    private PooledEffect defaultEffect;
    private ObjectMap<Action, ParticleEffectPool.PooledEffect> effectMap;
    public Action action=null;

    private String defaultKey;
    private String[] states;
    private String[] effectNames;

    @Override
    public BaseComponent create() {
        //defaultEffect= Assets.effectPools.get(defaultKey).obtain();
        effectMap = new ObjectMap<>();
        for (int i = 0; i < states.length; i++) {
            effectMap.put(Action.valueOf(states[i]), Assets.effectPools.get(effectNames[i]).obtain());

        }

            //effectMap.put(Action.valueOf(effects.name),Assets.effectPools.get(args.getString(effects.child.asString())).obtain());

        return this;
    }
    public ParticleComponent create(ParticleEffectPool.PooledEffect defaultEffect,ObjectMap<Action, ParticleEffectPool.PooledEffect> effects){

        this.defaultEffect=defaultEffect;
        this.effectMap=effects;

        return this;
    }

    public ParticleEffectPool.PooledEffect get() {
        if (effectMap != null && action != null) {
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
