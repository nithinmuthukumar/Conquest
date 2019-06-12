package com.nithinmuthukumar.conquest.UIDatas;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonValue;
import com.nithinmuthukumar.conquest.Assets;

public class ItemData extends Data {
    private int rarity;
    private String type;
    private String iconName;


    public ItemData(JsonValue value) {


        super(value.name, Assets.style.get(value.getString("icon"), TextureRegion.class), 0);
        iconName = value.getString("icon");
        rarity = value.getInt("rarity");
        type = value.getString("type");



    }


    public int getRarity() {
        return rarity;
    }

    public String getType() {
        return type;
    }

    public String getIconName() {
        return iconName;
    }

}
