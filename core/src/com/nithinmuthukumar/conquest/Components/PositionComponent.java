package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;

public class PositionComponent implements Component {
    public float x;
    public float y;
    public float z;
    public PositionComponent(float x,float y){
        this.x=x;
        this.y=y;
        this.z=0;
    }
    public PositionComponent(float x,float y,float z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public void moveBy(float x,float y){
        this.x+=x;
        this.y+=y;

    }
}
