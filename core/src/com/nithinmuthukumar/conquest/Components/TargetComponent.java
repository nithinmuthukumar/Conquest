package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.compression.lzma.Base;

public class TargetComponent implements BaseComponent {
    public Vector2 target;
    @Override
    public BaseComponent create(JsonValue args) {
        target.x=args.getFloat("x");
        target.y=args.getFloat("y");
        return this;
    }

    public TargetComponent create(Vector2 target) {
        this.target = target;
        return this;

    }

    @Override
    public void reset() {
        target=null;
    }


}
