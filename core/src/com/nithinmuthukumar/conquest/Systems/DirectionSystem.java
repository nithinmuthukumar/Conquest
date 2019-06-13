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

//sets the direction based on the angle
public class DirectionSystem extends IteratingSystem {
    public DirectionSystem() {
        super(Family.all(StateComponent.class).one(TargetComponent.class, VelocityComponent.class).get(), 4);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        float angle;
        //gets the angle from either the direction of the entities velocity and if it has no velocity (tower) use the target
        if (velocityComp.has(entity)) {
            angle = velocityComp.get(entity).angle();
        } else if (targetComp.get(entity).target != null) {
            angle = Utils.getTargetAngle(transformComp.get(entity).pos, targetComp.get(entity).target);
        } else {
            return;
        }
        //if the angle is less than 0 360 is added to get a positive version of the angle
        if (angle < 0) angle = 360 + angle;
        StateComponent state = stateComp.get(entity);
        //the angles direction is set based on how many ways the sprite can face
        if (state.numDirs == 8) {
            eightDir(state, angle);
        } else if (state.numDirs == 4) {
            fourDir(state, angle);

        } else if (state.numDirs == 2) {
            twoDir(state, angle);
        }


    }

    //separates the angles into eight portions and determines the direction
    public void eightDir(StateComponent state, float angle) {
        //the first value is either UP or DOWN and the second is LEFT or RIGHT which determines the overall direction when joined
        String[] dir = {"", ""};


        if (angle < 67.5 || angle > 292.5) dir[1] = "RIGHT";

        else if (angle > 112.5 && angle < 247.5) dir[1] = "LEFT";

        else dir[1] = "";

        if (angle > 90 - 67.5 && angle < 90 + 67.5) dir[0] = "UP";

        else if (angle > 270 - 67.5 && angle < 270 + 45 * 3 / 2f) dir[0] = "DOWN";

        else dir[0] = "";

        state.direction = Direction.valueOf(Utils.joinArray(dir));

    }

    //separates the circle into 4 portions and gets the direction based on that
    public void fourDir(StateComponent state, float angle) {

        if (angle <= 45 || angle > 315) state.direction = Direction.RIGHT;

        else if (angle <= 135 && angle > 45) state.direction = Direction.UP;

        else if (angle <= 225 && angle > 135) state.direction = Direction.LEFT;

        else state.direction = Direction.DOWN;
    }

    //2 portions where left part of circle is left and right is right
    public void twoDir(StateComponent state, float angle) {
        if (angle > 270 || angle <= 90) state.direction = Direction.RIGHT;

        else state.direction = Direction.LEFT;

    }
}
