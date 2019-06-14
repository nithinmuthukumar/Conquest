//interface that defines a method for chaining creation and also creating Components from the Json reflection
package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

import java.io.Serializable;


public interface BaseComponent extends Poolable, Component, Serializable {
    public BaseComponent create();
    //public String write();

}
