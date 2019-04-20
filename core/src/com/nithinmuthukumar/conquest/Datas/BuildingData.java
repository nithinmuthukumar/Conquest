package com.nithinmuthukumar.conquest.Datas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.nithinmuthukumar.conquest.Helpers.Assets;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;
import org.w3c.dom.Entity;

public class BuildingData implements EntityData {
    private String name;
    //public final int cost;
    private Texture image;



    private TiledMapTileLayer tileLayer;
    private Array<RectangleMapObject> collisionLayer;

    @Override
    public void write(Json json) {

    }

    @Override
    public void read(Json json, JsonValue value) {
        name=value.getString("name");
        //cost=value.getInt("cost");
        image = Assets.manager.get(value.getString("icon"));
        TiledMap m=Assets.manager.get(value.getString("mapPath"), TiledMap.class);

        tileLayer =(TiledMapTileLayer) m.getLayers().get("tileinfo");
        collisionLayer= m.getLayers().get("collisioninfo").getObjects().getByType(RectangleMapObject.class);

    }
    public String getName() {
        return name;
    }

    public Texture getImage() {
        return image;
    }

    public TiledMapTileLayer getTileLayer() {
        return tileLayer;
    }

    public Array<RectangleMapObject> getCollisionLayer() {
        return collisionLayer;
    }
    //cant spawn rn cuz game map is needed
    @Override
    public void spawn(float x, float y) {

    }
}
