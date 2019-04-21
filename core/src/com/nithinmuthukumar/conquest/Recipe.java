package com.nithinmuthukumar.conquest;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Helpers.Assets;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class Recipe{
    private JsonValue jsonData;



    public Entity make(){
        Entity e=Globals.engine.createEntity();

        for(JsonValue c:jsonData){
            try {
                Class<BaseComponent> clazz=ClassReflection.forName(c.name);
                e.add(Globals.engine.createComponent(clazz).create(c.child));
            } catch (ReflectionException ex) {
                ex.printStackTrace();
            }
        }
        return e;

    }
}
