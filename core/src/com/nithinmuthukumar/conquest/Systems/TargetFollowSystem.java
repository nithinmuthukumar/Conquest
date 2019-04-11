package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.nithinmuthukumar.conquest.Components.TargetComponent;
import com.nithinmuthukumar.conquest.Components.VelocityComponent;
import com.nithinmuthukumar.conquest.Helpers.Globals;

public class TargetFollowSystem extends IteratingSystem {
    public TargetFollowSystem() {
        super(Family.all(TargetComponent.class, VelocityComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        Vector2 start = new Vector2(Globals.transformComp.get(entity).x, Globals.transformComp.get(entity).y);
        Vector2 target = Globals.targetComp.get(entity).target;
        float angle = MathUtils.radiansToDegrees * MathUtils.atan2(target.y - start.y, target.x - start.x);
        VelocityComponent velocity = Globals.velocityComp.get(entity);
        velocity.setAngle(angle);
    }
}
