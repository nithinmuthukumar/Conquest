package com.nithinmuthukumar.conquest.Server;

public class PlayerMessage {
    public int x;
    public int y;
    public int id;

    //kryo requires all registered classes to have
    public PlayerMessage() {
    }


    public PlayerMessage(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }
}
