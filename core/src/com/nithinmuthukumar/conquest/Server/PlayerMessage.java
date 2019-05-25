package com.nithinmuthukumar.conquest.Server;

public class PlayerMessage {
    public int x;
    public int y;
    public int side;

    public PlayerMessage(int x, int y, int side) {
        this.side = side;
        this.x = x;
        this.y = y;
    }
}
