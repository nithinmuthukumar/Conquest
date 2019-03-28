package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RenderableComponent implements Component {
    public TextureRegion texture;
    public float alpha;
    public RenderableComponent(){
        alpha=1;

    }
    public RenderableComponent(Texture texture){
        this.texture=new TextureRegion(texture);
        alpha=1;

    }
    public RenderableComponent(TextureRegion textureRegion){
        texture=textureRegion;
        alpha=1;

    }
}
