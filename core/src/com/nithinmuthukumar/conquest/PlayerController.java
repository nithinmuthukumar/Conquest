package com.nithinmuthukumar.conquest;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Server.InputMessage;
import com.nithinmuthukumar.conquest.Server.WeaponSwitchMessage;

import static com.badlogic.gdx.Input.Keys.*;
import static com.nithinmuthukumar.conquest.Globals.*;
import static com.nithinmuthukumar.conquest.Helpers.Utils.setMeleeTransform;


public class PlayerController {
    private Entity weapon;
    private Entity player;
    private int x;
    private int y;


    public PlayerController(Entity player) {
        this.player = player;

    }




    public void keyDown(int keycode) {
        PlayerComponent p = playerComp.get(player);
        StateComponent state = stateComp.get(player);
        switch (keycode) {
            case R:
                state.action = Action.WALK;
                break;
            case NUM_1:

                state.action = Action.IDLE;
                if (p.meleeSlot == null) break;
                if (p.meleeUses <= 0) {
                    p.meleeSlot = null;
                    return;
                }
                p.meleeUses -= 1;
                weapon = Assets.recipes.get(p.meleeSlot).make().add(engine.createComponent(AllianceComponent.class).create(allianceComp.get(player).side));


                setMeleeTransform(player, weapon);

                engine.addEntity(weapon);
                break;


            case NUM_2:


                if (p.shootSlot == null) break;
                if (p.shootUses <= 0) {
                    p.shootSlot = null;
                    return;
                }
                p.shootUses -= 1;
                Entity shooter = Assets.recipes.get(p.shootSlot).make().add(engine.createComponent(AllianceComponent.class).create(allianceComp.get(player).side));
                if (targetComp.has(shooter)) {
                    targetComp.get(shooter).target = new Vector2(x, y);
                }
                if (velocityComp.has(shooter)) {
                    velocityComp.get(shooter).setAngle(velocityComp.get(player).angle());
                }
                transformComp.get(shooter).rotation = velocityComp.get(player).angle();
                bodyComp.get(shooter).body.setTransform(bodyComp.get(player).body.getPosition(), velocityComp.get(player).angle());

                engine.addEntity(shooter);
                break;
            case NUM_3:
                if (p.throwSlot == null) break;
                if (p.throwUses <= 0) {
                    p.throwSlot = null;
                    return;
                }
                p.throwUses -= 1;

                Entity w = Assets.recipes.get(p.throwSlot).make();
                setMeleeTransform(player, w);
                engine.addEntity(w);
                break;
            case NUM_4:


                if (p.shieldSlot == null) break;
                if (p.shieldUses <= 0) {
                    p.shieldSlot = null;

                    return;
                }
                p.shieldUses -= 1;
                state.action = Action.IDLE;

                if (engine.getEntities().contains(weapon, true)) {
                    engine.removeEntity(weapon);
                }

                weapon = Assets.recipes.get(playerComp.get(player).shieldSlot).make().add(engine.createComponent(AllianceComponent.class).create(allianceComp.get(player).side));
                transformComp.get(weapon).pos.set(transformComp.get(player).pos);
                setMeleeTransform(player, weapon);
                transformComp.get(weapon).rotation = 0;
                stateComp.get(weapon).direction = state.direction;

                engine.addEntity(weapon);
                break;



            case A:
                equipComp.get(player).equipping = true;
                break;
        }
    }


    public void keyUp(int keycode) {
        switch (keycode) {
            case R:
                stateComp.get(player).action = Action.IDLE;
                break;
            case A:
                equipComp.get(player).equipping = false;
                break;
            case NUM_1:
            case NUM_4:
                if (weapon == null) {
                    return;
                }
                if (shieldComp.has(weapon)) {
                    weapon.add(engine.createComponent(RemovalComponent.class));
                }
                weapon = null;
                break;
        }

    }

    public void mouseMoved(int mScreenX, int mScreenY) {
        if (weapon != null && shieldComp.has(weapon)) {

            return;

        }
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
            weapons.shootUses = 5 + Assets.itemDatas.get(object.weapon).getRarity() * 5;
        } else if (object.slot.equals("throw")) {
            weapons.throwSlot = object.weapon;
            weapons.throwUses = 1 + Assets.itemDatas.get(object.weapon).getRarity();
        } else if (object.slot.equals("melee")) {
            weapons.meleeSlot = object.weapon;
            weapons.meleeUses = 5 + Assets.itemDatas.get(object.weapon).getRarity() * 5;

        } else if (object.slot.equals("shield")) {
            weapons.shieldSlot = object.weapon;
            weapons.shieldUses = 5 + Assets.itemDatas.get(object.weapon).getRarity() * 5;
        }
    }
}

