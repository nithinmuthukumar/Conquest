package com.nithinmuthukumar.conquest.Components;

import com.nithinmuthukumar.conquest.UIDatas.BuildingData;

import static com.nithinmuthukumar.conquest.Globals.gameMap;

//component that is added to all buildings and holds data about what it is and where it was built
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
        //removes the tilelayer that was placed on the gamemap when it was created
        gameMap.removeLayer(data.getTileLayer(), builtx, builty);

        data=null;

    }
}
