package com.nithinmuthukumar.conquest.UIDatas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.nithinmuthukumar.conquest.Assets;

public class BuildingData extends Data {


    public TiledMapTileLayer tileLayer;
    public Array<RectangleMapObject> collisionLayer;
    public BuildingData(JsonValue value) {
        String mapPath=value.getString("mapPath");
        name=value.name;
        icon = Assets.manager.get(mapPath + ".png", Texture.class);
        TiledMap m = Assets.manager.get(mapPath + ".tmx", TiledMap.class);

        tileLayer = (TiledMapTileLayer) m.getLayers().get("tileinfo");
        collisionLayer = m.getLayers().get("collisioninfo").getObjects().getByType(RectangleMapObject.class);
    }

}
