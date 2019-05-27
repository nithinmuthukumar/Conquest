package com.nithinmuthukumar.conquest.Server;

public class BuildMessage {
    public String name;
    public int id;
    public int buildX;
    public int buildY;

    public BuildMessage(int id, int buildX, int buildY, String name) {
        this.id = id;
        this.name = name;
        this.buildX = buildX;
        this.buildY = buildY;
    }

    public BuildMessage() {

    }
}
