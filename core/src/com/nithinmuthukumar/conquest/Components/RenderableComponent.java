package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Pool;
import com.nithinmuthukumar.conquest.Helpers.Assets;

public class RenderableComponent implements BaseComponent {
    public TextureRegion region;
    public Color color=new Color();
    public float originX,originY;


    @Override
    public BaseComponent create(JsonValue args) {
        region =Assets.manager.get(args.getString("region"),TextureRegion.class);

        return create();
    }
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
