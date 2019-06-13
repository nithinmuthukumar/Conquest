package com.nithinmuthukumar.conquest.Components;

public class DropComponent implements BaseComponent {
    //the possible drops
    public String[] drops;
    //the amount of drops
    public int nums;
    //the range within which the drops are sent
    public int range;

    @Override
    public BaseComponent create() {
        return this;
    }

    @Override
    public void reset() {

    }
}
