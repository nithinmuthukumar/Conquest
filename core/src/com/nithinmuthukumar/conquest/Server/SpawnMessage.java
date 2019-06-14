package com.nithinmuthukumar.conquest.Server;

//sends a message of where the entity is spawned
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
