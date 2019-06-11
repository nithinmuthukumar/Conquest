package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.AnimationComponent;
import com.nithinmuthukumar.conquest.Components.RenderableComponent;
import com.nithinmuthukumar.conquest.Components.StateComponent;

import static com.nithinmuthukumar.conquest.Globals.*;
public class AnimationSystem extends IteratingSystem{

    public AnimationSystem(){
        super(Family.all(RenderableComponent.class, AnimationComponent.class, StateComponent.class).get(), 5);
    }

    @Override
    public void update(float deltaTime) {

        super.update(deltaTime);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        AnimationComponent animation = animationComp.get(entity);
        animation.aniTime+=deltaTime;

        StateComponent state=stateComp.get(entity);
        RenderableComponent renderable=renderComp.get(entity);
        renderable.region =animation.get(state.action,state.direction).getKeyFrame(animation.aniTime,true);


    }
}
