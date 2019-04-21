package com.nithinmuthukumar.conquest.Helpers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.*;
import com.nithinmuthukumar.conquest.Recipe;
import com.nithinmuthukumar.conquest.Utils;

import java.util.HashMap;


public class Assets {
    public static final AssetManager manager=new AssetManager();
    public static Skin style;
    public static HashMap<String, Recipe> recipes;
    public static Array<BuildingData> placeables;
    public static ObjectMap<String,BuildingData> nonPlaceables;
    private static JsonReader jsonReader=new JsonReader();
    public static ObjectMap<String,ParticleEffectPool> effectPools;



    //function to add all files to assetManager queue
    public static void loadAllFiles(){
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.setLoader(ParticleEffect.class,new ParticleEffectLoader(new InternalFileHandleResolver()));
        manager.load("themes/flat-earth/skin/flat-earth-ui.json",Skin.class);
        //manager.load("themes/shade/skin/uiskin.json",Skin.class);
        loadAllFilesInFolder("backgrounds");
        loadAllFilesInFolder("buildings");
        loadAllFilesInFolder("characters");
        loadAllFilesInFolder("ui stuff");
        loadAllFilesInFolder("hearts");

        manager.load("Particle Park Burnout/Particle Park Burnout.p", ParticleEffect.class);

        manager.finishLoading();

        style=manager.get("themes/flat-earth/skin/flat-earth-ui.json");
        //style=manager.get("themes/shade/skin/uiskin.json");
        Json json = new Json(JsonWriter.OutputType.minimal);
        placeables=new Array<>();
        JsonValue v=jsonReader.parse(new FileHandle("stats.json"));

        //buildings.size
        for (int i = 0; i < 1; i++) {
            String building=v.get("Buildings").get("placeable").get(i).toJson(JsonWriter.OutputType.minimal);
            BuildingData data=json.fromJson(BuildingData.class, building);
            placeables.add(data);
        }
        nonPlaceables=new ObjectMap<>();
        for (int i = 0; i < 3; i++) {
            String building=v.get("Buildings").get("nonPlaceable").get(i).toJson(JsonWriter.OutputType.minimal);
            BuildingData data=json.fromJson(BuildingData.class, building);
            nonPlaceables.put(data.getName(),data);
        }


        effectPools =new ObjectMap<>();

        ParticleEffect smokeEffect =manager.get("Particle Park Burnout/Particle Park Burnout.p", ParticleEffect.class);
        ParticleEffectPool smokeEffectPool = new ParticleEffectPool(smokeEffect,1,10);
        effectPools.put("burnout",smokeEffectPool);
    }
    private static void loadAllFilesInFolder(String path){
        loadAllFilesInFolder(new FileHandle(path));
    }
    private static void loadAllFilesInFolder(FileHandle file){

        for (FileHandle f : Utils.listFiles(file)) {
            if (f.isDirectory()) {
                loadAllFilesInFolder(f);
            } else {
                switch (f.extension()) {
                    case "png":
                    case "jpg":
                        manager.load(f.path(), Texture.class);
                        break;
                    case "tmx":

                        manager.load(f.path(),TiledMap.class);
                        break;
                    case "p":
                        manager.load(f.path(),ParticleEffect.class);
                }
            }
        }


    }



}
