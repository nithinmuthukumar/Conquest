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
    public static TextureAtlas itemPics;


    //function to add all files to assetManager queue
    public static void loadAllFiles() {
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.setLoader(ParticleEffect.class, new ParticleEffectLoader(new InternalFileHandleResolver()));
        manager.load("theme/theme.json", Skin.class);
        //manager.load("themes/shade/skin/uiskin.json",Skin.class);
        loadAllFilesInFolder("backgrounds");
        loadAllFilesInFolder("buildings");
        loadAllFilesInFolder("characters");
        loadAllFilesInFolder("effects");
        loadAllFilesInFolder("weapons");

        manager.load("icons.atlas", TextureAtlas.class);
        manager.load("inventory_icons.atlas", TextureAtlas.class);


        manager.finishLoading();

        TextureAtlas icons = manager.get("inventory_icons.atlas", TextureAtlas.class);
        itemPics = manager.get("icons.atlas", TextureAtlas.class);

        style = manager.get("theme/theme.json");

        style.addRegions(icons);

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
        for (FileHandle f : Utils.listFiles(new FileHandle("effects/"))) {
            if (!f.extension().equals("p"))
                continue;
            ParticleEffect effect = manager.get(f.path(), ParticleEffect.class);
            ParticleEffectPool pool = new ParticleEffectPool(effect, 1, 10);
            effectPools.put(f.nameWithoutExtension(), pool);
        }

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
