package com.nithinmuthukumar.conquest.Helpers;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.nithinmuthukumar.conquest.Enums.Action;

public class CParticle extends ParticleEffect {
    public Action key;
    public float offsetX,offsetY;
    public float timeTilDeath = 0.5f; // add a 1 second delay
    public boolean isDead = false;

}
