package com.nithinmuthukumar.conquest;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.nithinmuthukumar.conquest.Components.*;
import org.lwjgl.Sys;

import static java.lang.Integer.parseInt;

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
            try {

                Class<BaseComponent> clazz=ClassReflection.forName("com.nithinmuthukumar.conquest.Components."+c.name+"Component");
                BaseComponent component=Globals.engine.createComponent(clazz);
                json.readFields(component,c);
                e.add(component.create());
            } catch (ReflectionException ex) {
                ex.printStackTrace();
            }
        }
        return e;

    }
}
