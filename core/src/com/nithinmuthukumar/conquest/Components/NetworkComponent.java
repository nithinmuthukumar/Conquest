package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.compression.lzma.Base;

public class NetworkComponent implements BaseComponent {
    public String id;
    public NetworkComponent(String id){
        this.id=id;

    }

    @Override
    public BaseComponent create() {
        return this;
    }

    @Override
    public void reset() {

    }
}
