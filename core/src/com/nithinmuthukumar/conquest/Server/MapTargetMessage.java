package com.nithinmuthukumar.conquest.Server;

import com.badlogic.gdx.math.Rectangle;

//sends what a selection rectangle and the x and y of the target location
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
