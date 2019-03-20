package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;


public class VelocityComponent extends Vector2 implements Component {

    public VelocityComponent(float magnitude){
        setLength(magnitude);
    }
}
