package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Server.InputMessage;
import com.nithinmuthukumar.conquest.Server.WeaponSwitchMessage;

import static com.badlogic.gdx.Input.Keys.*;
import static com.nithinmuthukumar.conquest.Globals.*;


public class PlayerController {
    private Entity weapon;
    private Entity player;
    private int x;
    private int y;


    public PlayerController(Entity player) {
        this.player = player;

    }


    public static void setMeleeTransform(Entity bearer, Entity weapon, int offset) {
        Body body = bodyComp.get(weapon).body;
        Vector2 origin = transformComp.get(bearer).pos;

        TransformComponent transform = transformComp.get(weapon);

        switch (stateComp.get(bearer).direction) {
            case UP:
                body.setTransform(origin.x, origin.y + offset, 0);
                transform.rotation = 90;

                break;
            case DOWN:
                body.setTransform(origin.x, origin.y - offset, 0);
                transform.rotation = 270;
                break;
            case UPLEFT:
                body.setTransform(origin.x - offset, origin.y + offset, 0);
                transform.rotation = 135;
                break;
            case UPRIGHT:
                body.setTransform(origin.x + offset, origin.y + offset, 0);
                transform.rotation = 45;
                break;
            case LEFT:
                body.setTransform(origin.x - offset, origin.y, 0);
                transform.rotation = 180;
                break;
            case DOWNLEFT:
                body.setTransform(origin.x - offset, origin.y - offset, 0);
                transform.rotation = 225;
                break;
            case DOWNRIGHT:
                body.setTransform(origin.x + offset, origin.y - offset, 0);
                transform.rotation = 315;
                break;
            case RIGHT:
                body.setTransform(origin.x + offset, origin.y, 0);
                transform.rotation = 0;
                break;
        }
    }

    public void keyDown(int keycode) {

        if (Conquest.engine.getEntities().contains(weapon, true)) {
            Conquest.engine.removeEntity(weapon);
        }


        StateComponent state = stateComp.get(player);
        switch (keycode) {
            case R:
                state.action = Action.WALK;
                break;
            case NUM_1:
                state.action = Action.IDLE;
                if (playerComp.get(player).meleeSlot == null) break;
                weapon = Assets.recipes.get(playerComp.get(player).meleeSlot).make().add(Conquest.engine.createComponent(AllianceComponent.class).create(allianceComp.get(player).side));


                setMeleeTransform(player, weapon, 20);

                Conquest.engine.addEntity(weapon);
                break;
            case NUM_2:

                if (playerComp.get(player).shootSlot == null) break;
                Entity shooter = Assets.recipes.get(playerComp.get(player).shootSlot).make().add(Conquest.engine.createComponent(AllianceComponent.class).create(allianceComp.get(player).side));
                if (targetComp.has(shooter)) {
                    targetComp.get(shooter).target = new Vector2(x, y);
                }
                if (velocityComp.has(shooter)) {
                    velocityComp.get(shooter).setAngle(velocityComp.get(player).angle());
                }
                transformComp.get(shooter).rotation = velocityComp.get(player).angle();
                bodyComp.get(shooter).body.setTransform(bodyComp.get(player).body.getPosition(), velocityComp.get(player).angle());

                Conquest.engine.addEntity(shooter);
                break;
            case NUM_4:

                if (playerComp.get(player).shieldSlot == null) break;
                state.action = Action.IDLE;

                if (Conquest.engine.getEntities().contains(weapon, true)) {
                    Conquest.engine.removeEntity(weapon);
                }

                weapon = Assets.recipes.get(playerComp.get(player).shieldSlot).make().add(Conquest.engine.createComponent(AllianceComponent.class).create(allianceComp.get(player).side));
                transformComp.get(weapon).pos.set(transformComp.get(player).pos);
                setMeleeTransform(player, weapon, 8);
                transformComp.get(weapon).rotation = 0;
                stateComp.get(weapon).direction = state.direction;

                Conquest.engine.addEntity(weapon);
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
        x = mScreenX;
        y = mScreenY;
        VelocityComponent velocity = velocityComp.get(player);

        float angle = (float) Math.toDegrees(MathUtils.atan2(mScreenY - transformComp.get(player).pos.y, mScreenX - transformComp.get(player).pos.x));
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

    public void process(WeaponSwitchMessage object) {
        PlayerComponent weapons = playerComp.get(player);
        if (object.slot.equals("shoot")) {
            weapons.shootSlot = object.weapon;
        } else if (object.slot.equals("throw")) {
            weapons.throwSlot = object.weapon;
        } else if (object.slot.equals("melee")) {
            weapons.meleeSlot = object.weapon;
        } else if (object.slot.equals("shield")) {
            weapons.shieldSlot = object.weapon;
        }
    }
}

