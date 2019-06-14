package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Queue;
import com.nithinmuthukumar.conquest.Components.ParticleComponent;
import com.nithinmuthukumar.conquest.Components.RenderableComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import static com.nithinmuthukumar.conquest.Globals.*;

//Render manager renders everything in a sorted order
public class RenderManager extends SortedIteratingSystem {
    private ObjectMap<String, Color> tintColors = new ObjectMap<>();
    private Queue<ParticleEffectPool.PooledEffect> requests = new Queue<>();


    public RenderManager() {
        super(Family.one(ParticleComponent.class, RenderableComponent.class).all(TransformComponent.class).exclude().get(), Utils.zyComparator, 11);
        //the tints of colors are needed so that the alliances can be color coded
        //the colors are more white so that the color isn't too dark
        tintColors.put("Red", new Color(1, 0.6f, 0.6f, 1));
        tintColors.put("Blue", Color.SKY);
        tintColors.put("Green", new Color(0.7f, 1, 0.7f, 1));
        tintColors.put("White", Color.WHITE);
        tintColors.put("Purple", new Color(0.7f, 0.5f, 1, 0.8f));
    }


    @Override
    public void update(float deltaTime) {
        batch.setProjectionMatrix(camera.combined);

        //sorts the entities by their z value so that they are drawn in the proper order
        forceSort();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //this calls processEntity on all the entities within the family
        super.update(deltaTime);
        //loops through all effect requests and draws them till the effect is complete than removes it
        for (ParticleEffectPool.PooledEffect effect : requests) {

            effect.draw(batch, deltaTime);
            if (effect.isComplete()) {
                effect.free();
                requests.removeValue(effect, true);
            }
        }

        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        TransformComponent transform = transformComp.get(entity);
        if(renderComp.has(entity)){
            //tints the entity by its alliance
            RenderableComponent renderable = renderComp.get(entity);
            if (allianceComp.has(entity)) {
                batch.setColor(tintColors.get(colors[allianceComp.get(entity).side]));
            } else batch.setColor(1, 1, 1, 1);


            batch.draw(renderable.region, transform.getRenderX(), transform.getRenderY(), renderable.originX, renderable.originY,
                    renderable.region.getRegionWidth(), renderable.region.getRegionHeight(), 1, 1, transform.rotation);

        }

        if(particleComp.has(entity)){

            //if the entity has particle components the particle is drawn
            ParticleComponent particle = particleComp.get(entity);

            if(particle.get()!=null) {

                particle.get().setPosition(transform.pos.x, transform.pos.y);

                particle.get().draw(batch, deltaTime);
            }
        }


    }


    public void addParticleRequest(ParticleEffectPool.PooledEffect object) {
        requests.addLast(object);

    }
}
