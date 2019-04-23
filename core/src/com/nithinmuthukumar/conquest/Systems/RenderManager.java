package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.nithinmuthukumar.conquest.Components.Identifiers.InvisibleComponent;
import com.nithinmuthukumar.conquest.Components.ParticleComponent;
import com.nithinmuthukumar.conquest.Components.RenderableComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.Globals;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import static com.nithinmuthukumar.conquest.Globals.*;


public class RenderManager extends SortedIteratingSystem {
    public RenderManager() {
        super(Family.one(ParticleComponent.class, RenderableComponent.class).all(TransformComponent.class).exclude(InvisibleComponent.class).get(),new Utils.ZYComparator(),4);
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
        TransformComponent position = transformComp.get(entity);
        if(renderComp.has(entity)){

            RenderableComponent renderable = renderComp.get(entity);
            //bad stuff happening right here
            if(renderable.region==null){
                return ;
            }
            /////////////////////////////////
            Color c=batch.getColor();
            //batch.setColor(renderable.color);
            batch.draw(renderable.region, position.getRenderX(), position.getRenderY(),renderable.originX, renderable.originY,
                    renderable.region.getRegionWidth(),renderable.region.getRegionHeight(), 1,1,position.rotation);

        }
        if(particleComp.has(entity)){
            ParticleComponent particle=Globals.particleComp.get(entity);
            if(particle.get()!=null) {
                particle.get().setPosition(position.x, position.y);
                particle.get().draw(Globals.batch, deltaTime);
            }
        }


    }
}
