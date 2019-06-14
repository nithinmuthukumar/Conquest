package com.nithinmuthukumar.conquest.Components;

//identifies the player
public class PlayerComponent implements BaseComponent {
    //holds all equipped weapons and the amount of uses left for the weapon
    public String meleeSlot;
    public int meleeUses;
    public String shootSlot;
    public int shootUses;
    public String throwSlot;
    public int throwUses;
    public String shieldSlot;
    public int shieldUses;

    @Override
    public BaseComponent create() {


        return this;
    }

    @Override
    public void reset() {
        shieldSlot = null;
        meleeSlot = null;
        shootSlot = null;
        throwSlot = null;

    }

}
