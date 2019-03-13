package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nithinmuthukumar.conquest.Components.PositionComponent;
import com.nithinmuthukumar.conquest.Components.TextureComponent;
import com.nithinmuthukumar.conquest.ZYComparator;

//position animation state
public class RenderSystem extends SortedIteratingSystem {
    private SpriteBatch batch;
    private ComponentMapper<PositionComponent> positionComp = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<TextureComponent> textureComp = ComponentMapper.getFor(TextureComponent.class);


    public RenderSystem(SpriteBatch batch){
        super(Family.all(PositionComponent.class,
                TextureComponent.class).get(),new ZYComparator());

        this.batch=batch;
    }
    @Override
    public void update(float deltaTime) {
        forceSort();
        batch.begin();
        super.update(deltaTime);
        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent positionComp = this.positionComp.get(entity);
        TextureComponent textureComp = this.textureComp.get(entity);

        batch.draw(textureComp.texture,positionComp.x,positionComp.y);

    }
}
