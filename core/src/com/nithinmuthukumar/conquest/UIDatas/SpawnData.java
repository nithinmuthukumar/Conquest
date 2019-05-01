package com.nithinmuthukumar.conquest.UIDatas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;
import com.nithinmuthukumar.conquest.Assets;

public class SpawnData extends Data{

    public SpawnData(JsonValue val){
        name=val.name;
        icon= Assets.manager.get(val.getString("iconPath"), Texture.class);
    }
}
