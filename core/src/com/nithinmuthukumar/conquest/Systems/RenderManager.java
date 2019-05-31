package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.nithinmuthukumar.conquest.Components.Identifiers.InvisibleComponent;
import com.nithinmuthukumar.conquest.Components.ParticleComponent;
import com.nithinmuthukumar.conquest.Components.RenderableComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import static com.nithinmuthukumar.conquest.Globals.*;


public class RenderManager extends SortedIteratingSystem {
    public RenderManager() {
        super(Family.one(ParticleComponent.class, RenderableComponent.class).all(TransformComponent.class).exclude(InvisibleComponent.class).get(), Utils.zyComparator, 4);
    }



    @Override
    public void update(float deltaTime) {
        forceSort();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Conquest.batch.begin();
        super.update(deltaTime);
        Conquest.batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        TransformComponent transform = transformComp.get(entity);
        //if(healthComp.has(entity)){
        //  Conquest.batch.draw(Assets.icons.createSprite());
        //}
        if(renderComp.has(entity)){


            RenderableComponent renderable = renderComp.get(entity);


            Conquest.batch.draw(renderable.region, transform.getRenderX(), transform.getRenderY(), renderable.originX, renderable.originY,
                    renderable.region.getRegionWidth(), renderable.region.getRegionHeight(), 1, 1, transform.rotation);

        }

        if(particleComp.has(entity)){
            ParticleComponent particle = particleComp.get(entity);
            if(particle.get()!=null) {
                particle.get().setPosition(transform.pos.x, transform.pos.y);
                particle.get().draw(Conquest.batch, deltaTime);
            }
        }


    }
}
