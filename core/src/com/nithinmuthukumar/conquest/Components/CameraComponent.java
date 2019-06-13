package com.nithinmuthukumar.conquest.Components;

//identifies an entity that the camera should focus on
public class CameraComponent implements BaseComponent {

    @Override
    public BaseComponent create() {
        return this;
    }

    @Override
    public void reset() {

    }
}
