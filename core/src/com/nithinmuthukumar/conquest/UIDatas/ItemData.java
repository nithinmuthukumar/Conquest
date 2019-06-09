package com.nithinmuthukumar.conquest.UIDatas;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonValue;
import com.nithinmuthukumar.conquest.Assets;

public class ItemData extends Data {
    public final String rarity;
    public final String type;
    public final String iconName;

    public ItemData(JsonValue value) {


        super(value.name, Assets.style.get(value.getString("icon"), TextureRegion.class), 0);
        iconName = value.getString("icon");
        rarity = value.getString("rarity");
        type = value.getString("type");



    }

}
