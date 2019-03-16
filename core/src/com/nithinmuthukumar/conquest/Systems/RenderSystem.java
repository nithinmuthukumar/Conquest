package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nithinmuthukumar.conquest.Components.PositionComponent;
import com.nithinmuthukumar.conquest.Components.RenderableComponent;
import com.nithinmuthukumar.conquest.ZYComparator;

//position animation state
public class RenderSystem extends SortedIteratingSystem {
    private SpriteBatch batch;
    private ComponentMapper<PositionComponent> positionComp = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<RenderableComponent> textureComp = ComponentMapper.getFor(RenderableComponent.class);


    public RenderSystem(SpriteBatch batch){
        super(Family.all(PositionComponent.class,
                RenderableComponent.class).get(),new ZYComparator());

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
        PositionComponent position = this.positionComp.get(entity);
        RenderableComponent renderable = this.textureComp.get(entity);
        Color c=batch.getColor();
        batch.setColor(c.r,c.g,c.b,renderable.alpha);
        batch.draw(renderable.texture,position.x,position.y);

    }
}
