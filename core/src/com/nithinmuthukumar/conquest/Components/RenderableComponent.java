package com.nithinmuthukumar.conquest.Components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonValue;
import com.nithinmuthukumar.conquest.Assets;

public class RenderableComponent implements BaseComponent {
    public TextureRegion region;
    private String regionPath;
    public Color color=Color.WHITE;
    public float originX,originY;


    @Override
    public BaseComponent create() {
        if(regionPath!=null) {
            region = new TextureRegion(Assets.manager.get(regionPath, Texture.class));
        }
        return this;
    }

    public RenderableComponent create(Texture texture){
        this.region =new TextureRegion(texture);
        originX=region.getRegionWidth()/2;
        originY=region.getRegionHeight()/2;
        return this;
    }
    public RenderableComponent create(TextureRegion textureRegion){
        region =textureRegion;
        originX=region.getRegionWidth()/2;
        originY=region.getRegionHeight()/2;
        return this;

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
