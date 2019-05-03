package com.nithinmuthukumar.conquest;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.nithinmuthukumar.conquest.Components.BaseComponent;
import com.nithinmuthukumar.conquest.Helpers.Utils;

public class Recipe{
    private JsonValue jsonData;
    private Json json;
    public Recipe(Json json,JsonValue jsonData){
        this.jsonData=jsonData;
        this.json=json;

    }



    public Entity make(){

        Entity e=Globals.engine.createEntity();

        for(JsonValue c:jsonData){
            Class<BaseComponent> clazz = Utils.getComponentClass(c.name);
            BaseComponent component = Globals.engine.createComponent(clazz);
            json.readFields(component, c);
            e.add(component.create());

        }
        return e;

    }
}
