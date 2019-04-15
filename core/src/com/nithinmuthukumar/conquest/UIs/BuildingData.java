package com.nithinmuthukumar.conquest.UIs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.nithinmuthukumar.conquest.Helpers.Assets;

public class BuildingData {
    public final String name;
    //public final int cost;
    public final Texture image;
    public final TiledMapTileLayer tileLayer;
    public final Array<RectangleMapObject> collisionLayer;

    public BuildingData(JsonValue value){

        name=value.getString("name");
        //cost=value.getInt("cost");
        image = Assets.manager.get(value.getString("icon"));
        TiledMap m=Assets.manager.get(value.getString("mapPath"), TiledMap.class);

        tileLayer =(TiledMapTileLayer) m.getLayers().get("tileinfo");
        collisionLayer= m.getLayers().get("collisioninfo").getObjects().getByType(RectangleMapObject.class);
    }
}
