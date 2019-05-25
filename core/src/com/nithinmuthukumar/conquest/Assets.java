package com.nithinmuthukumar.conquest;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.*;
import com.nithinmuthukumar.conquest.Helpers.Utils;
import com.nithinmuthukumar.conquest.UIDatas.BuildingData;
import com.nithinmuthukumar.conquest.UIDatas.ItemData;
import com.nithinmuthukumar.conquest.UIDatas.SpawnData;

import java.util.HashMap;


public class Assets {
    public static final AssetManager manager=new AssetManager();
    public static Skin style;
    //all entity recipes
    public static HashMap<String, Recipe> recipes;
    private static JsonReader jsonReader=new JsonReader();
    public static ObjectMap<String,ParticleEffectPool> effectPools;
    public static OrderedMap<String, BuildingData> buildingDatas;
    public static OrderedMap<String, SpawnData> spawnDatas;
    public static OrderedMap<String, ItemData> itemDatas;
    public static TextureAtlas icons;



    //function to add all files to assetManager queue
    public static void loadAllFiles() {
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.setLoader(ParticleEffect.class, new ParticleEffectLoader(new InternalFileHandleResolver()));
        manager.load("themes/flat-earth/skin/flat-earth-ui.json", Skin.class);
        //manager.load("themes/shade/skin/uiskin.json",Skin.class);
        loadAllFilesInFolder("backgrounds");
        loadAllFilesInFolder("buildings");
        loadAllFilesInFolder("characters");
        loadAllFilesInFolder("ui stuff");
        loadAllFilesInFolder("hearts");

        manager.load("Particle Park Burnout/Particle Park Burnout.p", ParticleEffect.class);
        manager.load("icons.atlas", TextureAtlas.class);


        manager.finishLoading();

        icons = manager.get("icons.atlas", TextureAtlas.class);

        style = manager.get("themes/flat-earth/skin/flat-earth-ui.json");

        Json json = new Json();

        JsonValue stats = jsonReader.parse(new FileHandle("stats.json"));
        recipes = new HashMap<>();
        for (JsonValue val : stats) {
            recipes.put(val.name, new Recipe(json, val));
        }
        buildingDatas = new OrderedMap<>();
        for (JsonValue val : jsonReader.parse(new FileHandle("buildingDatas.json"))) {
            buildingDatas.put(val.name, new BuildingData(val));

        }
        spawnDatas = new OrderedMap<>();
        for (JsonValue val : jsonReader.parse(new FileHandle("spawnDatas.json"))) {
            spawnDatas.put(val.name, new SpawnData(val));

        }
        itemDatas = new OrderedMap<>();
        for (JsonValue val : jsonReader.parse(new FileHandle("itemDatas.json"))) {
            itemDatas.put(val.name, new ItemData(val));

        }


        effectPools = new ObjectMap<>();

        ParticleEffect smokeEffect = manager.get("Particle Park Burnout/Particle Park Burnout.p", ParticleEffect.class);
        ParticleEffectPool smokeEffectPool = new ParticleEffectPool(smokeEffect, 1, 10);
        effectPools.put("burnout", smokeEffectPool);
    }
    private static void loadAllFilesInFolder(String path){
        loadAllFilesInFolder(new FileHandle(path));
    }

    //recursively goes through all files in the directory and loads them
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
