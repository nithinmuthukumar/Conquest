package com.nithinmuthukumar.conquest.Server;

public class InputMessage extends Message {
    public String type;
    public int[] args;

    public InputMessage(String type, int... args) {
        this.type = type;
        this.args = args;
    }

    public InputMessage() {

    }

}
