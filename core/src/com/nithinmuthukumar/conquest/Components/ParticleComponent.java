package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.utils.Pool;

public class ParticleComponent implements Component, Pool.Poolable {
    public ParticleEffectPool.PooledEffect effect;
    public float timeTilDeath = 0.5f; // add a 1 second delay
    public boolean isDead = false;
    public float offsetX,offsetY;
    public ParticleComponent create(ParticleEffectPool.PooledEffect effect){
        this.effect=effect;
        return this;
    }

    @Override
    public void reset() {
        effect.free();
        isDead=false;
        timeTilDeath=0.5f;
    }
}
