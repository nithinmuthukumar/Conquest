package com.nithinmuthukumar.conquest.UIDatas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonValue;
import com.nithinmuthukumar.conquest.Assets;

public class SpawnData extends Data{

    public SpawnData(JsonValue val){
        super(val.name, new TextureRegion(Assets.manager.get(val.getString("icon"), Texture.class)), val.getInt("cost"));
    }
}
