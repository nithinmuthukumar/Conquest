package com.nithinmuthukumar.conquest.Components;

public class DecayComponent implements BaseComponent{
    //time left for the entity to live
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
