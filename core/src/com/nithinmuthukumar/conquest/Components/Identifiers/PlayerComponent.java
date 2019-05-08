package com.nithinmuthukumar.conquest.Components.Identifiers;

import com.nithinmuthukumar.conquest.Components.BaseComponent;
import com.nithinmuthukumar.conquest.Recipe;

public class PlayerComponent implements BaseComponent {
    public Recipe[] equipped;

    @Override
    public BaseComponent create() {
        equipped = new Recipe[4];


        return this;
    }

    @Override
    public void reset() {
        equipped = null;
    }

}
