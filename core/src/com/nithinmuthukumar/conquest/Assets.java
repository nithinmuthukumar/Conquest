// =-=-=-=-=-=-=-= CONQUEST =-=-=-=-=-=-=-=
// Preloads and organises all assets for easy access

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
    //manager loads all the pictures beforehand so that they only have to be loaded once
    public static final AssetManager manager=new AssetManager();
    //all entity recipes
    public static final HashMap<String, Recipe> recipes = new HashMap<>();
    public static final ObjectMap<String, ParticleEffectPool> effectPools = new ObjectMap<>();
    //containers holding the data of the various UI elements
    //ordered maps are used here because when iterating order must be the same so that
    //they can be drawn in order in the ui
    public static final OrderedMap<String, BuildingData> buildingDatas = new OrderedMap<>();
    public static final OrderedMap<String, SpawnData> spawnDatas = new OrderedMap<>();
    public static final OrderedMap<String, ItemData> itemDatas = new OrderedMap<>();
    private static final JsonReader jsonReader = new JsonReader();
    //style holds information that is used to create the UI as well as hold images that are part of an atlas
    public static Skin style;
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
        loadAllFilesInFolder("towers");

        manager.load("theme/icons.atlas", TextureAtlas.class);
        manager.load("theme/inventory_icons.atlas", TextureAtlas.class);


        manager.finishLoading();

        TextureAtlas icons = manager.get("theme/inventory_icons.atlas", TextureAtlas.class);
        //itemPics is not placed within style because they have the same names as the inventory icons but their size is different
        itemPics = manager.get("theme/icons.atlas", TextureAtlas.class);

        style = manager.get("theme/theme.json");
        //the icons texture atlas is placed in style
        style.addRegions(icons);
        //json object used to initialize components with reflection in recipe
        String currentDirectory = System.getProperty("user.dir");
        System.out.println("The current working directory is " + currentDirectory);
        Json json = new Json();
        //parsing all the json files to get information on all the entities
        JsonValue stats = jsonReader.parse(new FileHandle("data/stats.json"));
        for (JsonValue val : stats) {
            recipes.put(val.name, new Recipe(json, val));
        }
        for (JsonValue val : jsonReader.parse(new FileHandle("data/buildingDatas.json"))) {
            buildingDatas.put(val.name, new BuildingData(val));

        }
        for (JsonValue val : jsonReader.parse(new FileHandle("data/spawnDatas.json"))) {
            spawnDatas.put(val.name, new SpawnData(val));

        }
        for (JsonValue val : jsonReader.parse(new FileHandle("data/itemDatas.json"))) {
            itemDatas.put(val.name, new ItemData(val));
        }
        //get all the effects and create pools for them
        //pools are an efficient way to hold data that is created and destroyed frequently
        //the instances are only created when they are needed
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


    public static void dispose() {
        style.dispose();
        manager.dispose();
        itemPics.dispose();
    }
}
