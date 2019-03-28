package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.nithinmuthukumar.conquest.Utils;


public class VelocityComponent extends Vector2 implements Component {
    public VelocityComponent(float magnitude){
        super(1,0);
        scl(magnitude);

    }

    @Override
    public Vector2 setAngle(float degrees) {
        return super.setAngle(degrees);

    }
}
