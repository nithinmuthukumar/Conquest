package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Enums.Direction;

public class StateComponent implements Component, Pool.Poolable {
    public int numDirs;
    public Action action;
    public Direction direction;
    public StateComponent create(int numDirs,Action action,Direction direction) {
        this.numDirs = numDirs;
        this.action=action;
        this.direction=direction;
        return this;

    }
    @Override
    public void reset() {
        numDirs=0;
        action=null;
        direction=null;

    }
}

