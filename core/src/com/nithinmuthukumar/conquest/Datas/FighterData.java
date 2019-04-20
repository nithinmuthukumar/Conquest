package com.nithinmuthukumar.conquest.Datas;

import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.nithinmuthukumar.conquest.Components.PlayerComponent;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;
import com.nithinmuthukumar.conquest.Helpers.Globals;

public class FighterData implements EntityData{
    private String type;
    private  int[] fixture;
    private int speed;
    //use reflection to spawn with a method that is name n json file
    @Override
    public void spawn(float x,float y) {
        EntityFactory.createKnight(x,y, Globals.engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).first());

    }

    @Override
    public void write(Json json) {

    }

    @Override
    public void read(Json json, JsonValue jsonData) {

    }
}
