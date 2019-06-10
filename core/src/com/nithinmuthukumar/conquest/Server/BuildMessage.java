package com.nithinmuthukumar.conquest.Server;

public class BuildMessage extends Message {
    public String name;
    public int buildX;
    public int buildY;

    public BuildMessage(int buildX, int buildY, String name) {
        this.name = name;
        this.buildX = buildX;
        this.buildY = buildY;
    }

    public BuildMessage() {

    }
}
