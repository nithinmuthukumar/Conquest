package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;

public class RenderableComponent implements Component {
    public Texture texture;
    public float alpha;
    public RenderableComponent(){
        alpha=1;

    }
    public RenderableComponent(Texture texture){
        this.texture=texture;
        alpha=1;

    }
}
