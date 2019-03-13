package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;

public class TextureComponent implements Component {
    public Texture texture;
    public TextureComponent(){

    }
    public TextureComponent(Texture texture){
        this.texture=texture;

    }
}
