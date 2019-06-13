package com.nithinmuthukumar.conquest.Components;

import com.badlogic.gdx.math.Vector2;

//holds the speed of the entity
public class VelocityComponent extends Vector2 implements BaseComponent {
    private float magnitude;
    @Override
    public BaseComponent create() {
        set(1,0);
        scl(magnitude);
        return this;
    }

    public VelocityComponent create(float magnitude){
        set(1,0);
        this.magnitude=magnitude;
        scl(magnitude);
        return this;


    }
    @Override
    public void reset() {
        set(0,0);
        setAngle(0);
    }


}
