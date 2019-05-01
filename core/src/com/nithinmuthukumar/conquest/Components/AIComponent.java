package com.nithinmuthukumar.conquest.Components;

import com.nithinmuthukumar.conquest.Enums.Action;

import java.util.LinkedList;
import java.util.Stack;

public class AIComponent implements BaseComponent{
    String[] initialActions;
    LinkedList<Action> plan;


    @Override
    public BaseComponent create() {
        plan=new LinkedList<>();

        for(String s:initialActions){
            plan.add(Action.valueOf(s));
        }

        return this;
    }

    @Override
    public void reset() {
        plan=null;
        initialActions=null;


    }
}
