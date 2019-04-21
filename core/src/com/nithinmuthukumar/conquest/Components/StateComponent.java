package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Enums.Direction;

public class StateComponent implements BaseComponent {
    public int numDirs;
    public Action action;
    public Direction direction;
    @Override
    public BaseComponent create(JsonValue args) {
        numDirs=args.getInt("numDirs");
        action=Action.valueOf(args.getString("action"));
        direction=Direction.valueOf(args.getString("direction"));
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

