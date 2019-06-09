package com.nithinmuthukumar.conquest;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.nithinmuthukumar.conquest.Components.BaseComponent;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;
import com.nithinmuthukumar.conquest.Helpers.Utils;

public class Recipe{
    private JsonValue jsonData;
    private Json json;
    public Recipe(Json json,JsonValue jsonData){
        this.jsonData=jsonData;
        this.json=json;

    }



    public Entity make(){

        Entity e = Conquest.engine.createEntity();

        for(JsonValue c:jsonData){
            if (c.name.equals("Body")) {
                JsonValue fxs = c.get("fixtures");
                float[][] fixtures = new float[fxs.size][];
                for (int j = 0; j < fxs.size; j++) {
                    fixtures[j] = fxs.get(j).asFloatArray();
                }
                Body body = EntityFactory.bodyBuilder(e, c.getString("type"), c.get("shapes").asStringArray(),
                        fixtures, c.get("isSensor").asBooleanArray(), c.getInt("density"), c.getInt("friction"));
                e.add(Conquest.engine.createComponent(BodyComponent.class).create(body));
            } else {
                Class<BaseComponent> clazz = Utils.getComponentClass(c.name);
                BaseComponent component = Conquest.engine.createComponent(clazz);
                json.readFields(component, c);
                e.add(component.create());
            }

        }
        return e;

    }
}
