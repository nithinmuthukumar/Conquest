package com.nithinmuthukumar.conquest.Containers;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;

public class FighterData {
    public final String type;
    public final int[] fixture;
    public final int speed;

    public FighterData(JsonValue value){
        type=value.getString("type");
        fixture=value.asIntArray();
        speed=0;
    }
}
