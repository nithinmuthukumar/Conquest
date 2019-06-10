package com.nithinmuthukumar.conquest.Components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nithinmuthukumar.conquest.Assets;

public class RenderableComponent implements BaseComponent {
    public TextureRegion region;
    private String regionPath;
    public float originX,originY;


    @Override
    public BaseComponent create() {

        if(regionPath!=null) {
            region = new TextureRegion(Assets.manager.get(regionPath, Texture.class));
            originX = region.getRegionWidth() / 2;
            originY = region.getRegionHeight() / 2;

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


    @Override
    public void reset() {
        region =null;
        originX = 0;
        originY = 0;
    }


}
