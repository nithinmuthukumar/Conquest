package com.nithinmuthukumar.conquest.Components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.nithinmuthukumar.conquest.Helpers.Assets;

public class BuildingComponent implements BaseComponent{
    private String name;
    private Texture image;
    private TiledMapTileLayer tileLayer;
    private Array<RectangleMapObject> collisionLayer;
    @Override
    public BaseComponent create(JsonValue args) {
        name=args.getString("name");
        image= Assets.manager.get(args.getString("image"), Texture.class);
        TiledMap m=Assets.manager.get(args.getString("mapPath"), TiledMap.class);

        tileLayer =(TiledMapTileLayer) m.getLayers().get("tileinfo");
        collisionLayer= m.getLayers().get("collisioninfo").getObjects().getByType(RectangleMapObject.class);
        return this;
    }

    @Override
    public void reset() {

    }
}
