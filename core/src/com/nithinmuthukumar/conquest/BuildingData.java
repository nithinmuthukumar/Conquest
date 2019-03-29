package com.nithinmuthukumar.conquest;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;

public class BuildingData {
    public final String name;
    public final int cost;
    public final Texture icon;
    public final String mapPath;
    public BuildingData(JsonValue value){
        name=value.getString("name");
        cost=value.getInt("cost");
        icon=Assets.manager.get(value.getString("icon"));
        mapPath=value.getString("mapPath");
    }
}
