package com.nithinmuthukumar.conquest.Server;

//is sent from the inventory UI to signal that a weapon was switched in the ui
public class WeaponSwitchMessage extends Message {
    public String weapon;
    public String slot;

    public WeaponSwitchMessage() {

    }

    public WeaponSwitchMessage(String weapon, String slot) {
        this.weapon = weapon;
        this.slot = slot;
    }
}
