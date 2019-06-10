package com.nithinmuthukumar.conquest.Server;

import com.badlogic.gdx.math.Rectangle;


public class MapTargetMessage extends Message {
    public Rectangle selection;
    public float x;
    public float y;
    public Rectangle mapDimensions;

    public MapTargetMessage(Rectangle area, Rectangle mapDimensions, float x, float y) {
        this.selection = area;
        this.x = x;
        this.y = y;
        this.mapDimensions = mapDimensions;


    }


    public MapTargetMessage() {
    }


}
