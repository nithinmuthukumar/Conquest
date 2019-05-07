package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.nithinmuthukumar.conquest.Components.StateComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.Components.VelocityComponent;
import com.nithinmuthukumar.conquest.Enums.Action;

import static com.badlogic.gdx.Input.Keys.*;
import static com.nithinmuthukumar.conquest.Globals.*;


public class PlayerController{



    private boolean on=true;

    private Listener<Integer> keyDownListener = (Signal<Integer> signal, Integer keycode) -> {
        if (on) {
            StateComponent state = stateComp.get(player.getEntity());
            switch (keycode) {
                case R:
                    state.action = Action.WALK;
                    break;
                case NUM_1:
                    if (playerComp.get(player.getEntity()).equipped.size >= 1) {
                        engine.addEntity(playerComp.get(player.getEntity()).equipped.get(0));
                    }
                    break;
                case A:
                    equipComp.get(player.getEntity()).equipping = true;
                    break;
            }


        }
    };
    private Listener<Integer> keyUpListener = (signal, keycode) -> {
        if (on) {
            StateComponent state = stateComp.get(player.getEntity());
            switch (keycode) {
                case R:
                    state.action = Action.IDLE;
                    break;
                case NUM_1:
                    //state.action = Action.BOWRELEASE;
                    if (playerComp.get(player.getEntity()).equipped.size >= 1)
                        engine.removeEntity(playerComp.get(player.getEntity()).equipped.get(0));
                    TransformComponent transform = transformComp.get(player.getEntity());
                    //EntityFactory.createArrow(transform.x, transform.y, Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
                    break;
                case A:
                    equipComp.get(player.getEntity()).equipping = false;


            }


        }
    };
    private Listener<int[]> mouseMovedListener = (Signal<int[]> signal, int[] object) -> {
        if (on) {
            int screenX = object[0];
            int screenY = object[1];
            VelocityComponent velocity = velocityComp.get(player.getEntity());
            TransformComponent position = transformComp.get(player.getEntity());
            //angle is found assuming the player is in the center of the screen
            //because mouse coordinates are relative to the screen
            float angle = (float) Math.toDegrees(Math.atan2(720 / 2 - screenY, screenX - 960 / 2));
            velocity.setAngle(angle);
        }
    };

    public PlayerController() {

        inputHandler.addListener("keyDown",keyDownListener);
        inputHandler.addListener("keyUp",keyUpListener);
        inputHandler.addListener("mouseMoved",mouseMovedListener);
    }


    public void off(){

        on=false;
        stateComp.get(player.getEntity()).action = Action.IDLE;
    }
    public void on(){
        on=true;
    }

    public void flip() {
        if (on) off();
        else on();
    }





}

