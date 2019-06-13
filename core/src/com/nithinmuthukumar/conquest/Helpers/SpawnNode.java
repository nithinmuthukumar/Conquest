package com.nithinmuthukumar.conquest.Helpers;

//simple node which counts keeps track of how long its been at the top of the queue
public class SpawnNode{

    public float timer;
    public String name;

    public SpawnNode(String name, int timer) {
        this.name = name;
        this.timer=timer;

    }
}
