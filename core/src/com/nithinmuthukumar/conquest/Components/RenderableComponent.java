package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

public class RenderableComponent implements Component, Pool.Poolable {
    public TextureRegion region;
    public float alpha=1;
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

    @Override
    public void reset() {
        region =null;
        alpha=1;
    }
}
