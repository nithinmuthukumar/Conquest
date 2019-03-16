package com.nithinmuthukumar.conquest;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Systems.MapCollisionSystem;

import java.util.ArrayList;

public class EntityFactory {
    public static void createPlayer(Engine engine){
        Entity e=new Entity();
        e.add(new PositionComponent(500,500));
        e.add(new AnimationComponent("Character/"));
        e.add(new PlayerComponent());
        e.add(new StateComponent());
        e.add(new VelocityComponent(1.2f));
        e.add(new RenderableComponent());
        e.add(new CameraComponent());
        engine.addEntity(e);
    }
    public static void createMap(int x, int y, String file, Engine engine, TmxMapLoader mapLoader){
        PositionComponent mapPos=new PositionComponent(0,0);
        ArrayList<Entity> entities=new ArrayList<>();
        TiledMap map=mapLoader.load(file+"/map.tmx");

        for(MapLayer layer:map.getLayers()){
            if(!layer.getName().equals("tileinfo")&&!layer.getName().equals("renderinfo")) {
                Entity l=new Entity();
                l.add(new RenderableComponent(new Texture(file + "/"+layer.getName() + ".png")));
                l.add(new PositionComponent(0,0,layer.getProperties().get("z",Integer.class)));
                engine.addEntity(l);
            }
        }
        MapCollisionSystem.addCollisionLayer((TiledMapTileLayer)map.getLayers().get("tileinfo"),x,y);
        //still have to finish this
        //going to get another tmx file from this and recursively add it to the map
        for(MapObject object: map.getLayers().get("renderinfo").getObjects()){
            if(object.getName().equals("roof")){
                Entity e=new Entity();
                e.add(new RenderableComponent(new Texture(object.getProperties().get("asset").toString())));
                e.add(new PositionComponent(mapPos.x+object.getProperties().get("x",Integer.class),object.getProperties().get("y",Integer.class),Integer.MAX_VALUE));
                e.add(new RoofComponent());
                engine.addEntity(e);
            }

        }


    }
    public static void createMapNavigator(int initX,int initY,int deviation,Engine engine){
        Entity e=new Entity();
        e.add(new PositionComponent(initX,initY));
        e.add(new MouseComponent());
        e.add(new CameraComponent());
        engine.addEntity(e);
    }
}
