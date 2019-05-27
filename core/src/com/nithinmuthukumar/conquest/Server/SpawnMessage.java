package com.nithinmuthukumar.conquest.Server;

public class SpawnMessage {
    public int id;
    public String name;
    public float x;
    public float y;

    public SpawnMessage(int id, String name, float x, float y) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;

    }

    public SpawnMessage() {

    }

}
