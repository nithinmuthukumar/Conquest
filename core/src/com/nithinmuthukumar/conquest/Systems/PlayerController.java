package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.Input;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Components.Identifiers.EnemyComponent;
import com.nithinmuthukumar.conquest.Components.Identifiers.PlayerComponent;
import com.nithinmuthukumar.conquest.Enums.Action;

import static com.nithinmuthukumar.conquest.Globals.*;

public class PlayerController{



    private Entity player;

    private boolean on=true;

    private Listener<Integer> keyDownListener = (Signal<Integer> signal, Integer keycode) -> {
        if (on) {
            StateComponent state = stateComp.get(player);

            if (keycode == Input.Keys.R)
                state.action = Action.WALK;

            if (keycode == Input.Keys.NUM_1) {
                //state.action = Action.BOWHOLD;
            }

        }
    };
    private Listener<Integer> keyUpListener = (signal, keycode) -> {
        if (on) {
            StateComponent state = stateComp.get(player);
            if (keycode == Input.Keys.R)
                state.action = Action.IDLE;

            if (keycode == Input.Keys.NUM_1) {
                //state.action = Action.BOWRELEASE;
                TransformComponent transform = transformComp.get(player);
                //EntityFactory.createArrow(transform.x, transform.y, Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            }
        }
    };
    private Listener<int[]> mouseMovedListener = (Signal<int[]> signal, int[] object) -> {
        if (on) {
            int screenX = object[0];
            int screenY = object[1];
            VelocityComponent velocity = velocityComp.get(player);
            TransformComponent position = transformComp.get(player);
            //angle is found assuming the player is in the center of the screen
            //because mouse coordinates are relative to the screen
            float angle = (float) Math.toDegrees(Math.atan2(720 / 2 - screenY, screenX - 960 / 2));
            velocity.setAngle(angle);
        }
    };

    public PlayerController() {
        try {
            this.player = engine.getEntitiesFor(Family.all(PlayerComponent.class).exclude(EnemyComponent.class).get()).first();
        } catch (NullPointerException e) {
            throw new RuntimeException("player not created");

        }
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

    public void flip() {
        on = !on;
    }





}

