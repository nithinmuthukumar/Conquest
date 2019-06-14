package com.nithinmuthukumar.conquest.UIDatas;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonValue;
import com.nithinmuthukumar.conquest.Assets;

public class ItemData extends Data {
    private int rarity;
    private String type;
    private String iconName;


    public ItemData(JsonValue value) {


        super(value.name, Assets.style.get(value.getString("icon"), TextureRegion.class), value.getInt("cost"));
        setIconName(value.getString("icon"));
        setRarity(value.getInt("rarity"));
        setType(value.getString("type"));


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

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}
