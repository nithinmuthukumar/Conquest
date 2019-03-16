package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.nithinmuthukumar.conquest.Components.*;

public class MouseFollowSystem extends EntitySystem {
    private ComponentMapper<PositionComponent> pm=ComponentMapper.getFor(PositionComponent.class);
    ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        entities=engine.getEntitiesFor(Family.all(MouseComponent.class).get());
        super.addedToEngine(engine);
    }

    @Override
    public void update(float deltaTime) {
        for(Entity e:entities){
            PositionComponent positionComp=pm.get(e);
            positionComp.x= Gdx.graphics.getWidth()-Gdx.input.getX();
            positionComp.y=Gdx.input.getY();

        }
        super.update(deltaTime);
    }
}
