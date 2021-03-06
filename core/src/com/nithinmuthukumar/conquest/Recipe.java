package com.nithinmuthukumar.conquest;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.nithinmuthukumar.conquest.Components.BaseComponent;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;
import com.nithinmuthukumar.conquest.Helpers.Utils;

//Recipe was an idea I came up with myself where it basically uses reflection to create an entity from
//json. This has the added advantage of not being as verbose as well as allowing easy addition of values
public class Recipe{
    private JsonValue jsonData;
    private Json json;
    public Recipe(Json json,JsonValue jsonData){
        this.jsonData=jsonData;
        this.json=json;

    }


    //returns an entity with all the data within the jsondata
    public Entity make(){

        Entity e = Globals.engine.createEntity();
        //loops through the components of the entity and creates them through reflection
        for(JsonValue c:jsonData){
            //body is special because its fields were too difficult to create with reflection so it was created manually
            if (c.name.equals("Body")) {
                JsonValue fxs = c.get("fixtures");
                float[][] fixtures = new float[fxs.size][];
                for (int j = 0; j < fxs.size; j++) {
                    fixtures[j] = fxs.get(j).asFloatArray();
                }
                Body body = EntityFactory.bodyBuilder(e, c.getString("type"), c.get("shapes").asStringArray(),
                        fixtures, c.get("isSensor").asBooleanArray(), c.getInt("density"), c.getInt("friction"));
                e.add(Globals.engine.createComponent(BodyComponent.class).create(body));
            } else {
                Class<BaseComponent> clazz = Utils.getComponentClass(c.name);
                BaseComponent component = Globals.engine.createComponent(clazz);
                //does the reflection on all the fields and matches them with the keys in the json
                json.readFields(component, c);
                e.add(component.create());
            }

        }
        return e;

    }
}
