package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class PositionComponent  extends Vector3 implements Component{
    public PositionComponent(float x, float y, float z){
        super(x,y,z);

    }

}
