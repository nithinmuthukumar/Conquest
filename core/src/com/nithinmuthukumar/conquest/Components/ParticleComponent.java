package com.nithinmuthukumar.conquest.Components;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.utils.ObjectMap;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Enums.Action;

//holds the particle data of an entity and the particle that should be run when an entity is executing a particular task
public class ParticleComponent implements BaseComponent {
    //the effect that runs when there is no state
    private PooledEffect defaultEffect;
    //the state to effect map
    private ObjectMap<Action, ParticleEffectPool.PooledEffect> effectMap;
    public Action action=null;
    //fields for reflection
    private String defaultKey;
    private String[] states;
    private String[] effectNames;

    @Override
    public BaseComponent create() {
        //if the reflection yields the value we create it
        if (defaultKey != null) {

            defaultEffect = Assets.effectPools.get(defaultKey).obtain();
            defaultEffect.start();
        }
        if (states != null) {
            effectMap = new ObjectMap<>();
            for (int i = 0; i < states.length; i++) {
                effectMap.put(Action.valueOf(states[i]), Assets.effectPools.get(effectNames[i]).obtain());

            }
        }

            //effectMap.put(Action.valueOf(effects.name),Assets.effectPools.get(args.getString(effects.child.asString())).obtain());

        return this;
    }
    public ParticleComponent create(ParticleEffectPool.PooledEffect defaultEffect,ObjectMap<Action, ParticleEffectPool.PooledEffect> effects){

        this.defaultEffect=defaultEffect;
        this.effectMap=effects;

        return this;
    }
    //returns the value in the map if there is an action otherwise the defaulteffect is returned regardless if it was null

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
        defaultEffect.free();
        if (effectMap != null) {
            for (PooledEffect p : effectMap.values()) {
                p.free();
            }
        }
        defaultEffect = null;
        defaultKey = null;
        effectMap = null;
    }


}
