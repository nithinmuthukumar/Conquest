package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.nithinmuthukumar.conquest.Action;
import com.nithinmuthukumar.conquest.Direction;

public class StateComponent implements Component {
    public int numDirs;

    public StateComponent(int numDirs) {
        this.numDirs = numDirs;

    }
    public Action action=Action.WALK;
    public Direction direction=Direction.UP;



}

