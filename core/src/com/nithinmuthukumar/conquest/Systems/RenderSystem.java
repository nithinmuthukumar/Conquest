package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nithinmuthukumar.conquest.Components.RenderableComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.ZYComparator;

import static com.nithinmuthukumar.conquest.Utils.renderComp;
import static com.nithinmuthukumar.conquest.Utils.transformComp;

public class RenderSystem extends SortedIteratingSystem {
    private SpriteBatch batch;


    public RenderSystem(SpriteBatch batch){
        super(Family.all(TransformComponent.class,
                RenderableComponent.class).get(),new ZYComparator(),4);

        this.batch=batch;
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
        RenderableComponent renderable = renderComp.get(entity);
        Color c=batch.getColor();
        batch.setColor(c.r,c.g,c.b,renderable.alpha);
        batch.draw(renderable.texture, position.getRenderX(), position.getRenderY());
    }
}
