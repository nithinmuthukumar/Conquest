package com.nithinmuthukumar.conquest.Server;

//gives information about the buttons that the client pressed
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
