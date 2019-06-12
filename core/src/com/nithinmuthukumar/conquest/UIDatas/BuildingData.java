package com.nithinmuthukumar.conquest.UIDatas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
        super(value.name, new TextureRegion(Assets.manager.get(value.getString("mapPath") + ".png", Texture.class)), value.getInt("cost"));

        TiledMap m = Assets.manager.get(value.getString("mapPath") + ".tmx", TiledMap.class);

        tileLayer = (TiledMapTileLayer) m.getLayers().get("tileinfo");
//        while (i.hasNext()){
//            System.out.println(i.next().getName());
//
//        }
//        System.out.println(value.name);
        collisionLayer = m.getLayers().get("collisioninfo").getObjects().getByType(RectangleMapObject.class);
    }

}
