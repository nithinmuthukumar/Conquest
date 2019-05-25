package com.nithinmuthukumar.conquest.Components;

public class DropComponent implements BaseComponent {
    public String[] drops;
    public int nums;
    public int range;

    @Override
    public BaseComponent create() {
        return this;
    }

    @Override
    public void reset() {

    }
}
