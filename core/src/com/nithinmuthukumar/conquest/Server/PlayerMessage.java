package com.nithinmuthukumar.conquest.Server;

public class PlayerMessage {
    public int x;
    public int y;
    public int id;

    public PlayerMessage() {

    }

    public PlayerMessage(int x, int y, int id) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
}
