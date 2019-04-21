package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

import java.io.Serializable;

public interface BaseComponent extends Poolable, Component, Serializable {
    public BaseComponent create(JsonValue args);
    //public String write();

}
