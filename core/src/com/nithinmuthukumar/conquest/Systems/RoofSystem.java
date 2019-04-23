package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Rectangle;
import com.nithinmuthukumar.conquest.Components.Identifiers.PlayerComponent;
import com.nithinmuthukumar.conquest.Components.RenderableComponent;
import com.nithinmuthukumar.conquest.Components.RoofComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;

public class RoofSystem extends IteratingSystem {
    private ComponentMapper<TransformComponent> positionComp = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<RenderableComponent> textureComp = ComponentMapper.getFor(RenderableComponent.class);
    public RoofSystem(){
        super(Family.all(TransformComponent.class,
                RenderableComponent.class, RoofComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent position = positionComp.get(entity);
        RenderableComponent renderable=textureComp.get(entity);
        Rectangle r=new Rectangle(position.x,position.y,renderable.region.getRegionWidth(),renderable.region.getRegionHeight());
        for (Entity e : getEngine().getEntitiesFor(Family.all(PlayerComponent.class, TransformComponent.class).get())) {

            if(r.contains(positionComp.get(e).x,positionComp.get(e).y)){
                renderable.color.a=0.3f;
                return;
            }
        }
        renderable.color.a=1;
    }
}
