package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.nithinmuthukumar.conquest.Components.ParticleComponent;
import com.nithinmuthukumar.conquest.Components.RenderableComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.Utils;

import static com.nithinmuthukumar.conquest.Helpers.Globals.*;


public class RenderManager extends SortedIteratingSystem {
    private ParticleRenderer particleRenderer=new ParticleRenderer();
    private RenderSystem renderSystem=new RenderSystem();
    public RenderManager() {
        super(Family.one(ParticleComponent.class, RenderableComponent.class).all(TransformComponent.class).get(),new Utils.ZYComparator(),5);
    }



    @Override
    public void update(float deltaTime) {
        forceSort();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        super.update(deltaTime);
        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if(particleComp.has(entity)){

            particleRenderer.processEntity(entity,deltaTime);

        }
        if(renderComp.has(entity)){
            renderSystem.processEntity(entity,deltaTime);
        }


    }
}
