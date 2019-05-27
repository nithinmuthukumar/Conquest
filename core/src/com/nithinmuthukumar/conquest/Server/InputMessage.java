package com.nithinmuthukumar.conquest.Server;

public class InputMessage {
    public int id;
    public String type;
    public int[] args;

    public InputMessage(int id, String type, int... args) {
        this.id = id;
        this.type = type;
        this.args = args;
    }

    public InputMessage() {

    }

}
