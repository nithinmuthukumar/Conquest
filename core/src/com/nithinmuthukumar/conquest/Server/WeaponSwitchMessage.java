package com.nithinmuthukumar.conquest.Server;

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
