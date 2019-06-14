package com.nithinmuthukumar.conquest.Components;

//defines the time left for the entity to live
public class DecayComponent implements BaseComponent{

    public float countDown;
    @Override
    public BaseComponent create() {
        return this;
    }

    @Override
    public void reset() {
        countDown=0;

    }
}
