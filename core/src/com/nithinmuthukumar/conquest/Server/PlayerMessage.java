package com.nithinmuthukumar.conquest.Server;

//holds info on where the player is placed and it's id
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
