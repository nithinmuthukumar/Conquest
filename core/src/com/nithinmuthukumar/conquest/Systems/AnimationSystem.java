package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.AnimationComponent;
import com.nithinmuthukumar.conquest.Components.RenderableComponent;
import com.nithinmuthukumar.conquest.Components.StateComponent;
import com.nithinmuthukumar.conquest.Enums.Action;

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
        //gets the animation and increments the animation time
        AnimationComponent animation = animationComp.get(entity);
        animation.aniTime+=deltaTime;

        StateComponent state=stateComp.get(entity);
        RenderableComponent renderable=renderComp.get(entity);
        //gets the animation and sets the region as that value
        renderable.region =animation.get(state.action,state.direction).getKeyFrame(animation.aniTime,true);
        //if the entity is done being born change the movement to walk
        if (state.action == Action.BIRTH) {
            if (animation.isAnimationFinished(Action.BIRTH, state.direction)) {
                state.action = Action.WALK;
            }
        }


    }
}
