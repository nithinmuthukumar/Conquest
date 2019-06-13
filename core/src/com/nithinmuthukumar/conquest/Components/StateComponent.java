package com.nithinmuthukumar.conquest.Components;

import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Enums.Direction;

//holds the state of the entity
public class StateComponent implements BaseComponent {
    //the number of directions that the entity can face
    public int numDirs;
    public Action action;
    public Direction direction;
    //fields for reflection
    private String actionString;
    private String directionString;
    @Override
    public BaseComponent create() {
        action=Action.valueOf(actionString);
        direction=Direction.valueOf(directionString);
        return this;
    }
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

