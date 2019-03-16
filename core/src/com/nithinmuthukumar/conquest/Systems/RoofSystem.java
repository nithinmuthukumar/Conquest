package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.nithinmuthukumar.conquest.Components.PlayerComponent;
import com.nithinmuthukumar.conquest.Components.PositionComponent;
import com.nithinmuthukumar.conquest.Components.RenderableComponent;
import com.nithinmuthukumar.conquest.Components.RoofComponent;

public class RoofSystem extends IteratingSystem {
    private ComponentMapper<PositionComponent> positionComp = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<RenderableComponent> textureComp = ComponentMapper.getFor(RenderableComponent.class);
    public RoofSystem(){
        super(Family.all(PositionComponent.class,
                RenderableComponent.class, RoofComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position=positionComp.get(entity);
        RenderableComponent renderable=textureComp.get(entity);
        Rectangle r=new Rectangle(position.x,position.y,renderable.texture.getWidth(),renderable.texture.getHeight());
        for(Entity e:getEngine().getEntitiesFor(Family.all(PlayerComponent.class,PositionComponent.class).get())){

            if(r.contains(positionComp.get(e).x,positionComp.get(e).y)){
                renderable.alpha=0.3f;
                return;
            }
        }
        renderable.alpha=1;
    }
}
