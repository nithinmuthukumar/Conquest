package com.nithinmuthukumar.conquest.Components.UIComponents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.BaseComponent;

public class BuildingComponent implements BaseComponent {
    public String name;
    public Texture image;
    private String mapPath;

    public TiledMapTileLayer tileLayer;
    public Array<RectangleMapObject> collisionLayer;
    @Override
    public BaseComponent create() {
        image= Assets.manager.get(mapPath+".png", Texture.class);
        TiledMap m=Assets.manager.get(mapPath+".tmx", TiledMap.class);

        tileLayer =(TiledMapTileLayer) m.getLayers().get("tileinfo");
        collisionLayer= m.getLayers().get("collisioninfo").getObjects().getByType(RectangleMapObject.class);
        return this;
    }

    @Override
    public void reset() {

    }
}
