package com.nithinmuthukumar.conquest.UIDatas;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Data {
    private String name;

    private TextureRegion icon;
    private int cost;

    public Data(String name, TextureRegion icon, int cost) {
        this.setName(name);
        this.setIcon(new TextureRegion(icon));
        this.setCost(cost);

    }

    @Override
    public String toString() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TextureRegion getIcon() {
        return icon;
    }

    public void setIcon(TextureRegion icon) {
        this.icon = icon;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
