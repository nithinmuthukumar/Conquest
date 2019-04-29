package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.nithinmuthukumar.conquest.Components.AnimationComponent;
import com.nithinmuthukumar.conquest.Components.StateComponent;
import com.nithinmuthukumar.conquest.Components.RenderableComponent;
import static com.nithinmuthukumar.conquest.Globals.*;
public class AnimationSystem extends IteratingSystem{
    private float aniTime=0;


    public AnimationSystem(){
        super(Family.all(RenderableComponent.class,AnimationComponent.class,StateComponent.class).get(),0);
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
