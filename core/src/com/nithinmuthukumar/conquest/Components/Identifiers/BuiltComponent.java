package com.nithinmuthukumar.conquest.Components.Identifiers;

import com.nithinmuthukumar.conquest.Components.BaseComponent;
import com.nithinmuthukumar.conquest.UIDatas.BuildingData;

import static com.nithinmuthukumar.conquest.Conquest.gameMap;

//component that is added to all buildings
public class BuiltComponent implements BaseComponent {
    public BuildingData data;
    public int builtx, builty;

    @Override
    public BaseComponent create() {
        return this;
    }

    public BaseComponent create(BuildingData data, int x, int y) {
        this.data=data;
        this.builtx = x;
        this.builty = y;
        return this;

    }

    @Override
    public void reset() {

        gameMap.removeLayer(data.tileLayer, builtx, builty);

        data=null;

    }
}
