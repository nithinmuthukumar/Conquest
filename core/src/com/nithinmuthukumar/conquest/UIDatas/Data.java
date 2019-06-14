package com.nithinmuthukumar.conquest.UIDatas;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Data {
    public String name;

    public TextureRegion icon;
    public int cost;

    public Data(String name, TextureRegion icon, int cost) {
        this.name = name;
        this.icon = new TextureRegion(icon);
        this.cost = cost;

    }

    @Override
    public String toString() {
        return name;
    }

}
