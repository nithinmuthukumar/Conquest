package com.nithinmuthukumar.conquest.UIDatas;

import com.badlogic.gdx.utils.JsonValue;
import com.nithinmuthukumar.conquest.Assets;

public class ItemData extends Data {
    public String rarity;
    public String type;

    public ItemData(JsonValue value) {

        super(value.name, Assets.icons.createSprite(value.getString("icon")), 0);
        rarity = value.getString("rarity");
        type = value.getString("type");



    }

}
