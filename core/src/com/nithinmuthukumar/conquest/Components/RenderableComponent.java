package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

public class RenderableComponent implements Component, Pool.Poolable {
    public TextureRegion region;
    public Color color=new Color();
    public float originX,originY;



    public RenderableComponent create(){
        originX=region.getRegionWidth()/2;
        originY=region.getRegionHeight()/2;
        return this;
    }
    public RenderableComponent create(Texture texture){
        this.region =new TextureRegion(texture);

        return create();

    }
    public RenderableComponent create(TextureRegion textureRegion){
        region =textureRegion;
        return create();

    }
    public RenderableComponent create(Color color){
        this.color=color;
        return this;

    }


    @Override
    public void reset() {
        region =null;
        color.set(1,1,1,1);
    }
}
