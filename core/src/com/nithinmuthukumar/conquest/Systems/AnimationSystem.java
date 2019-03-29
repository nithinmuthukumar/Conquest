package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.nithinmuthukumar.conquest.Components.AnimationComponent;
import com.nithinmuthukumar.conquest.Components.StateComponent;
import com.nithinmuthukumar.conquest.Components.RenderableComponent;

public class AnimationSystem extends EntitySystem{
    private float aniTime=0;
    private ImmutableArray<Entity> entities;
    private ComponentMapper<RenderableComponent> tm = ComponentMapper.getFor(RenderableComponent.class);
    private ComponentMapper<AnimationComponent> am = ComponentMapper.getFor(AnimationComponent.class);
    private ComponentMapper<StateComponent> sm = ComponentMapper.getFor(StateComponent.class);

    public AnimationSystem(){
        super(0);
    }
    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(RenderableComponent.class,
                AnimationComponent.class,StateComponent.class).get());
    }
    @Override
    public void update(float deltaTime) {
        aniTime+=deltaTime;
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            AnimationComponent animationComp = am.get(entity);
            StateComponent stateComp=sm.get(entity);
            RenderableComponent textureComp=tm.get(entity);
            textureComp.texture=animationComp.get(stateComp.action,stateComp.direction).getKeyFrame(aniTime,true);

        }
    }
}
