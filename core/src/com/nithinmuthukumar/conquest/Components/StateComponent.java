package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.nithinmuthukumar.conquest.Action;
import com.nithinmuthukumar.conquest.Direction;

public class StateComponent implements Component {
    public Action action=Action.WALK;
    public Direction direction=Direction.UP;



}

