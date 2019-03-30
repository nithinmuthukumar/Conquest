package com.nithinmuthukumar.conquest;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;


public class Assets {
    public static final AssetManager manager=new AssetManager();
    public static Skin style;
    public static Array<BuildingData> buildingDatas;
    private static JsonReader jsonReader=new JsonReader();



    //function to add all files to assetManager queue
    public static void loadAllFiles(){
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("themes/shade/skin/uiskin.json",Skin.class);
        loadAllFilesInFolder("backgrounds");
        loadAllFilesInFolder("buildings");
        loadAllFilesInFolder("characters");
        manager.finishLoading();
        style=manager.get("themes/shade/skin/uiskin.json");
        buildingDatas=new Array<>();
        JsonValue buildings=jsonReader.parse(new FileHandle("stats.json")).get("Buildings");
        //buildings.size
        for(int i=0;i<1;i++){
            buildingDatas.add(new BuildingData(buildings.get(i)));

        }
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
                }
            }
        }


    }



}
