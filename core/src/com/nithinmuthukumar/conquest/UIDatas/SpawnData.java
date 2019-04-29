package com.nithinmuthukumar.conquest.UIDatas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;
import com.nithinmuthukumar.conquest.Assets;

public class SpawnData {
    public String name;

    public Texture icon;
    public SpawnData(JsonValue val){
        name=val.name;
        icon= Assets.manager.get(val.getString("iconPath"), Texture.class);
    }
}
