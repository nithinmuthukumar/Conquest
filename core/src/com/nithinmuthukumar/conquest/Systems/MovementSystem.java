package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.nithinmuthukumar.conquest.Components.*;

//position velocity state
public class MovementSystem extends IteratingSystem {
    private ComponentMapper<PositionComponent> pm=ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm=ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<MovingComponent> mm=ComponentMapper.getFor(MovingComponent.class);
    public MovementSystem(){
        super(Family.all(
                PositionComponent.class,
                VelocityComponent.class, MovingComponent.class).get());

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position=pm.get(entity);
        VelocityComponent velocity=vm.get(entity);



        if(!mm.get(entity).collide){
            position.x+=velocity.moveDistX();
            position.y+=velocity.moveDistY();

        }


    }
}
