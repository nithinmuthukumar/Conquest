package com.nithinmuthukumar.conquest.Server;

//holds data on the spot where the item is placed and it's name
public class ItemMessage {
    public String name;
    public int x;
    public int y;

    public ItemMessage() {

    }

    public ItemMessage(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;

    }
}
