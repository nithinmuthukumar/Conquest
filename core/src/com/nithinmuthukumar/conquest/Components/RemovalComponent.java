package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.compression.lzma.Base;

public class RemovalComponent implements BaseComponent {

    public float countDown;

    @Override
    public BaseComponent create(JsonValue args) {
        countDown=args.getFloat("countDown");
        return this;
    }

    public RemovalComponent create(float countDown) {
        this.countDown=countDown;
        return this;
    }

    @Override
    public void reset() {
        countDown=0;

    }



}
