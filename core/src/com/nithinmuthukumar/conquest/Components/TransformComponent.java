package com.nithinmuthukumar.conquest.Components;

import com.badlogic.gdx.math.Vector2;

//holds all the transformation data of an object
public class TransformComponent implements BaseComponent {
    public float width, height;
    //z indicates the order in which the entities are drawn
    public int z;
    public float rotation;
    public Vector2 pos;
    //fields for reflection
    int x, y;

    @Override
    public BaseComponent create() {
        pos = new Vector2(x, y);
        return this;
    }

    public TransformComponent create(float x, float y, int z, float width, float height) {
        pos = new Vector2(x, y);
        this.width = width;
        this.height = height;
        this.z = z;


        return this;

    }

    //the position is the center of the sprite so when drawing the position has to be shifted
    public float getRenderX() {
        return pos.x - width / 2;
    }

    public float getRenderY() {
        return pos.y - height / 2;

    }

    @Override
    public void reset() {
        pos = null;
        rotation=0;
        width=0;
        height=0;
        z=0;

    }


}
