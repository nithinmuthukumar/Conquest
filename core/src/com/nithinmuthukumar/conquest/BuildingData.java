package com.nithinmuthukumar.conquest;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.JsonValue;

public class BuildingData {
    public final String name;
    public final int cost;
    public final Texture image;
    public final TiledMapTileLayer tileLayer;
    public BuildingData(JsonValue value){
        name=value.getString("name");
        cost=value.getInt("cost");
        image = Assets.manager.get(value.getString("icon"));

        tileLayer = (TiledMapTileLayer) Assets.manager.get(value.getString("mapPath"), TiledMap.class).getLayers().get("tileinfo");
    }
}
