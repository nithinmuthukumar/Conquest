package com.nithinmuthukumar.conquest.Server;

public class SpawnMessage extends Message {
    public String name;
    public float x;
    public float y;

    public SpawnMessage(String name, float x, float y) {
        this.name = name;
        this.x = x;
        this.y = y;

    }

    public SpawnMessage() {

    }

}
