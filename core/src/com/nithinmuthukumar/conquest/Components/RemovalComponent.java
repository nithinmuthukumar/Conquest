package com.nithinmuthukumar.conquest.Components;

//identifies and entity that is about to be removed and is only staying for the death animation
public class RemovalComponent implements BaseComponent {

    public float countDown;

    @Override
    public BaseComponent create() {
        return this;
    }

    public RemovalComponent create(float countDown) {
        this.countDown=countDown;
        return this;
    }

    @Override
    public void reset() {
        countDown=0;

    }



}
