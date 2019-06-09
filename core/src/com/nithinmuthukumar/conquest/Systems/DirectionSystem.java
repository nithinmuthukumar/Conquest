package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.StateComponent;
import com.nithinmuthukumar.conquest.Components.TargetComponent;
import com.nithinmuthukumar.conquest.Components.VelocityComponent;
import com.nithinmuthukumar.conquest.Enums.Direction;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import static com.nithinmuthukumar.conquest.Globals.*;

public class DirectionSystem extends IteratingSystem {
    public DirectionSystem() {
        super(Family.all(StateComponent.class).one(TargetComponent.class, VelocityComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        float angle;
        if (velocityComp.has(entity)) {
            angle = velocityComp.get(entity).angle();
        } else if (targetComp.get(entity).target != null) {

            angle = Utils.getTargetAngle(transformComp.get(entity).pos, targetComp.get(entity).target);
        } else {
            return;
        }
        if (angle < 0) angle = 360 + angle;
        StateComponent state = stateComp.get(entity);
        if (state.numDirs == 8) {
            eightDir(state, angle);
        } else if (state.numDirs == 4) {
            fourDir(state, angle);

        } else if (state.numDirs == 2) {
            twoDir(state, angle);
        }


    }

    public void eightDir(StateComponent state, float angle) {
        String[] dir = {"", ""};

        if (angle < 67.5 || angle > 292.5) dir[1] = "RIGHT";

        else if (angle > 112.5 && angle < 247.5) dir[1] = "LEFT";

        else dir[1] = "";

        if (angle > 90 - 67.5 && angle < 90 + 67.5) dir[0] = "UP";

        else if (angle > 270 - 67.5 && angle < 270 + 45 * 3 / 2) dir[0] = "DOWN";

        else dir[0] = "";

        state.direction = Direction.valueOf(Utils.joinArray(dir));

    }

    public void fourDir(StateComponent state, float angle) {

        if (angle <= 45 || angle > 315) state.direction = Direction.RIGHT;

        else if (angle <= 135 && angle > 45) state.direction = Direction.UP;

        else if (angle <= 225 && angle > 135) state.direction = Direction.LEFT;

        else state.direction = Direction.DOWN;
    }

    public void twoDir(StateComponent state, float angle) {
        if (angle > 270 || angle <= 90) state.direction = Direction.RIGHT;

        else state.direction = Direction.LEFT;

    }
}
