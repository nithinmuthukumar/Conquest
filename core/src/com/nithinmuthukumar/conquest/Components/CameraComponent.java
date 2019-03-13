package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraComponent implements Component {
    public Entity target;
    public OrthographicCamera camera;
    public CameraComponent(OrthographicCamera camera,Entity target){
        this.camera=camera;
        this.target=target;



    }
}
