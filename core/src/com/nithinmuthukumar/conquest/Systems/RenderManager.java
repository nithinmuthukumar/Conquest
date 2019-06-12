package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.ParticleComponent;
import com.nithinmuthukumar.conquest.Components.RenderableComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import static com.nithinmuthukumar.conquest.Conquest.batch;
import static com.nithinmuthukumar.conquest.Conquest.colors;
import static com.nithinmuthukumar.conquest.Globals.*;


public class RenderManager extends SortedIteratingSystem {
    ObjectMap<String, Color> tintColors = new ObjectMap<>();
    Array<ParticleEffectPool.PooledEffect> requests = new Array<>();
    public final Listener<String> particleRequestListener = (signal, object) -> requests.add(Assets.effectPools.get(object).obtain());


    public RenderManager() {
        super(Family.one(ParticleComponent.class, RenderableComponent.class).all(TransformComponent.class).exclude().get(), Utils.zyComparator, 11);
    }



    @Override
    public void update(float deltaTime) {
        tintColors.put("Red", new Color(1, 0.6f, 0.6f, 1));
        tintColors.put("Blue", Color.SKY);
        tintColors.put("White", Color.WHITE);

        forceSort();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        super.update(deltaTime);
        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        TransformComponent transform = transformComp.get(entity);
        if (healthComp.has(entity)) {
            ProgressBar progressBar = new ProgressBar(0, healthComp.get(entity).maxHealth, 0.5f, false, Assets.style);
            progressBar.setValue(healthComp.get(entity).health);
            progressBar.setScaleY(2);
            progressBar.setPosition(transform.getRenderX(), transform.getRenderY() + transform.height);
            progressBar.draw(batch, 1);
        }
        if(renderComp.has(entity)){



            RenderableComponent renderable = renderComp.get(entity);
            if (allianceComp.has(entity)) {
                batch.setColor(tintColors.get(colors[allianceComp.get(entity).side]));
            } else batch.setColor(1, 1, 1, 1);


            batch.draw(renderable.region, transform.getRenderX(), transform.getRenderY(), renderable.originX, renderable.originY,
                    renderable.region.getRegionWidth(), renderable.region.getRegionHeight(), 1, 1, transform.rotation);

        }

        if(particleComp.has(entity)){
            ParticleComponent particle = particleComp.get(entity);
            if(particle.get()!=null) {
                particle.get().setPosition(transform.pos.x, transform.pos.y);
                particle.get().draw(batch, deltaTime);
            }
        }


    }
}
