package com.nithinmuthukumar.conquest.Components.Identifiers;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.JsonValue;
import com.nithinmuthukumar.conquest.Components.BaseComponent;
import com.nithinmuthukumar.conquest.UIDatas.BuildingData;

public class BuiltComponent implements BaseComponent {
    public BuildingData data;
    @Override
    public BaseComponent create() {
        return this;
    }
    public BaseComponent create(BuildingData data){
        this.data=data;
        return this;

    }

    @Override
    public void reset() {
        data=null;

    }
}
