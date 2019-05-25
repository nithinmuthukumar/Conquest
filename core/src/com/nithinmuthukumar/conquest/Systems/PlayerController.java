package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.InputAdapter;
import com.nithinmuthukumar.conquest.Components.StateComponent;
import com.nithinmuthukumar.conquest.Components.VelocityComponent;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import static com.badlogic.gdx.Input.Keys.*;
import static com.nithinmuthukumar.conquest.Globals.*;


public class PlayerController extends InputAdapter {
    private Entity weapon;



    private boolean on=true;

    public PlayerController() {

    }


    public void off() {

        on = false;
        stateComp.get(Conquest.player.getEntity()).action = Action.IDLE;
    }

    public void on() {
        on = true;
    }

    public void flip() {
        if (on) off();
        else on();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (on) {
            StateComponent state = stateComp.get(Conquest.player.getEntity());
            switch (keycode) {
                case R:
                    state.action = Action.WALK;
                    break;
                case NUM_1:
                    if (Conquest.engine.getEntities().contains(weapon, true)) {
                        Conquest.engine.removeEntity(weapon);
                    }
                    if (playerComp.get(Conquest.player.getEntity()).equipped.length >= 1) {
                        weapon = playerComp.get(Conquest.player.getEntity()).equipped[0].make();
                        Utils.setWeaponTransform(Conquest.player.getEntity(), weapon);
                        Conquest.engine.addEntity(weapon);

                    }
                    break;
                case A:
                    equipComp.get(Conquest.player.getEntity()).equipping = true;
                    break;
            }


        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (on) {
            StateComponent state = stateComp.get(Conquest.player.getEntity());
            switch (keycode) {
                case R:
                    state.action = Action.IDLE;
                    break;
                case A:
                    equipComp.get(Conquest.player.getEntity()).equipping = false;
                    break;




            }

        }
        return super.keyUp(keycode);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if (on) {
            VelocityComponent velocity = velocityComp.get(Conquest.player.getEntity());
            //angle is found assuming the player is in the center of the screen
            //because mouse coordinates are relative to the screen
            float angle = (float) Math.toDegrees(Math.atan2(720 / 2 - screenY, screenX - 960 / 2));
            velocity.setAngle(angle);
        }
        return super.mouseMoved(screenX, screenY);
    }




}

