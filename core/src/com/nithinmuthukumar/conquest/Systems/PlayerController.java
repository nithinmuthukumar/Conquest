package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.Input;
import com.nithinmuthukumar.conquest.Action;
import com.nithinmuthukumar.conquest.Components.StateComponent;
import com.nithinmuthukumar.conquest.Components.VelocityComponent;
import com.nithinmuthukumar.conquest.Direction;
import com.nithinmuthukumar.conquest.Utils;

import static com.nithinmuthukumar.conquest.Conquest.inputHandler;
import static com.nithinmuthukumar.conquest.Utils.stateComp;
import static com.nithinmuthukumar.conquest.Utils.velocityComp;

public class PlayerController{



    private Entity player;
    private String[] dir = new String[]{"", ""};
    private boolean on=true;
    private Listener<Integer> keyDownListener = (Signal<Integer> signal, Integer keycode) -> {
        if (on) {
            StateComponent state = stateComp.get(player);

            if (keycode == Input.Keys.R)
                state.action = Action.WALK;

            if (keycode == Input.Keys.SPACE)
                state.action = Action.ATTACK;

        }
    };
    private Listener<Integer> keyUpListener = (signal, keycode) -> {
        if (on) {
            StateComponent state = stateComp.get(player);
            if (keycode == Input.Keys.R)
                state.action = Action.IDLE;

        }
    };
    private Listener<int[]> mouseMovedListener = (Signal<int[]> signal, int[] object) -> {
        if (on) {
            int screenX = object[0];
            int screenY = object[1];
            StateComponent state = stateComp.get(player);
            VelocityComponent velocity = velocityComp.get(player);


            float angle = (float) Math.toDegrees(Math.atan2(720 / 2 - screenY, screenX - 960 / 2));

            if (angle < 0) angle = 360 + angle;

            if (angle < 67.5 || angle > 292.5) dir[1] = "RIGHT";

            else if (angle > 112.5 && angle < 247.5) dir[1] = "LEFT";

            else dir[1] = "";

            if (angle > 90 - 67.5 && angle < 90 + 67.5) dir[0] = "UP";

            else if (angle > 270 - 67.5 && angle < 270 + 45 * 3 / 2) dir[0] = "DOWN";

            else dir[0] = "";

            state.direction = Direction.valueOf(Utils.joinArray(dir));
            velocity.setAngle(angle);
        }
    };


    public PlayerController(Entity player){
        this.player=player;
        inputHandler.addListener("keyDown",keyDownListener);
        inputHandler.addListener("keyUp",keyUpListener);
        inputHandler.addListener("mouseMoved",mouseMovedListener);
    }
    public void off(){
        on=false;
    }
    public void on(){
        on=true;
    }





}

