package com.nithinmuthukumar.conquest;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class CollisionLayer extends TiledMapTileLayer {
    public CollisionLayer(int width, int height, int tileWidth, int tileHeight) {
        super(width, height, tileWidth, tileHeight);
    }
    public void addCollisionLayer(TiledMapTileLayer layer,int posX,int posY){
        Utils.print("CollisionLayer",Integer.toString(layer.getWidth()));
        for(int x=0;x<layer.getWidth();x++) {
            for (int y = 0; y < layer.getHeight(); y++){

                setCell((int)(x+posX/layer.getTileWidth()), (int) (y+posY/layer.getTileHeight()),layer.getCell(x,y));

            }
        }
    }

    public Cell getCell(float x,float y){
        return getCell(Math.round(x),Math.round(y));
    }
}
