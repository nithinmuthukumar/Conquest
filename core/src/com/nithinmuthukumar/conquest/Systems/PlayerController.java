package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.nithinmuthukumar.conquest.Components.StateComponent;
import com.nithinmuthukumar.conquest.Components.VelocityComponent;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Helpers.Utils;
import com.nithinmuthukumar.conquest.Server.InputMessage;

import static com.badlogic.gdx.Input.Keys.*;
import static com.nithinmuthukumar.conquest.Globals.*;


public class PlayerController {
    private Entity weapon;
    private Entity player;


    public PlayerController(Entity player) {
        this.player = player;

    }


    public void keyDown(int keycode) {

        StateComponent state = stateComp.get(player);
        switch (keycode) {
            case R:
                state.action = Action.WALK;
                break;
            case NUM_1:
                if (Conquest.engine.getEntities().contains(weapon, true)) {
                    Conquest.engine.removeEntity(weapon);
                }
                if (playerComp.get(player).equipped.length >= 1) {
                    weapon = playerComp.get(player).equipped[0].make();
                    Utils.setWeaponTransform(player, weapon);
                    Conquest.engine.addEntity(weapon);

                }
                break;
            case A:
                equipComp.get(player).equipping = true;
                break;
        }


    }


    public void keyUp(int keycode) {
        StateComponent state = stateComp.get(player);
            switch (keycode) {
                case R:
                    state.action = Action.IDLE;
                    break;
                case A:
                    equipComp.get(player).equipping = false;
                    break;




            }

    }

    public void mouseMoved(int mScreenX, int mScreenY) {
        VelocityComponent velocity = velocityComp.get(player);

        float angle = (float) Math.toDegrees(MathUtils.atan2(mScreenY - transformComp.get(player).y, mScreenX - transformComp.get(player).x));
        velocity.setAngle(angle);

    }


    public void process(InputMessage inputMessage) {
        int[] args = inputMessage.args;
        switch (inputMessage.type) {
            case "mouseMoved":
                mouseMoved(args[0], args[1]);
                break;
            case "keyUp":
                keyUp(args[0]);
                break;
            case "keyDown":
                keyDown(args[0]);
        }
    }
}

