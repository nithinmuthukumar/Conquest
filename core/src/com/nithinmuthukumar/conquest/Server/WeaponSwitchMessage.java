package com.nithinmuthukumar.conquest.Server;

public class WeaponSwitchMessage {
    public String weapon;
    public String slot;
    public int id;

    public WeaponSwitchMessage() {

    }

    public WeaponSwitchMessage(int id, String weapon, String slot) {
        this.id = id;
        this.weapon = weapon;
        this.slot = slot;
    }
}
