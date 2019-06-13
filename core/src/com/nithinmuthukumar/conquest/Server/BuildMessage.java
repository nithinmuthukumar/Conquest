package com.nithinmuthukumar.conquest.Server;

//holds the information about the entity that built
public class BuildMessage extends Message {
    public String name;
    public int buildX;
    public int buildY;

    public BuildMessage(String name, int buildX, int buildY) {
        this.name = name;
        this.buildX = buildX;
        this.buildY = buildY;
    }

    public BuildMessage() {

    }
}
