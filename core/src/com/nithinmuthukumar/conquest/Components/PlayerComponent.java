package com.nithinmuthukumar.conquest.Components;

public class PlayerComponent implements BaseComponent {
    //holds all equipped weapons
    public String meleeSlot;
    public String shootSlot;
    public String throwSlot;
    public String shieldSlot;

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
