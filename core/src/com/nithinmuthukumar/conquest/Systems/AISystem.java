package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.nithinmuthukumar.conquest.Action;
import com.nithinmuthukumar.conquest.Components.EnemyComponent;
import com.nithinmuthukumar.conquest.Components.PlayerComponent;
import com.nithinmuthukumar.conquest.Components.StateComponent;
import com.nithinmuthukumar.conquest.Components.VelocityComponent;
import com.nithinmuthukumar.conquest.Constants;

public class AISystem extends IteratingSystem {
    public AISystem(){
        super(Family.all(EnemyComponent.class).exclude(PlayerComponent.class).get());
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        StateComponent state = Constants.stateComp.get(entity);

        Vector2 start = new Vector2(Constants.transformComp.get(entity).x, Constants.transformComp.get(entity).y);
        Entity target = Constants.targetComp.get(entity).target;
        Vector2 end = new Vector2(Constants.transformComp.get(target).x, Constants.transformComp.get(target).y);
        if (start.dst(end) <= Constants.fighterComp.get(entity).range) {
            state.action = Action.ATTACK;
        } else {
            state.action = Action.WALK;
        }
        float angle = MathUtils.radiansToDegrees * MathUtils.atan2(end.y - start.y, end.x - start.x);
        VelocityComponent velocity = Constants.velocityComp.get(entity);
        velocity.setAngle(angle);
    }
}
