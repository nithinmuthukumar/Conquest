package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;


public class VelocityComponent implements Component {
    public float magnitude;
    public float angle;
    public VelocityComponent(float magnitude){
        this.magnitude=magnitude;



    }
    public float moveDistX(){

        return (float)(magnitude*Math.cos(Math.toRadians(angle)));
    }
    public float moveDistY(){

        return (float)(magnitude*Math.sin(Math.toRadians(angle)));
    }
}
