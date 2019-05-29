package com.nithinmuthukumar.conquest;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;

import static com.nithinmuthukumar.conquest.Globals.NO_TILE;

public class GameMap {
    private TiledMapTileLayer collisionLayer;

    public GameMap(int width, int height, int tileWidth, int tileHeight) {


        collisionLayer = new TiledMapTileLayer(width, height, tileWidth, tileHeight);

    }

    //need to add rotation here
    public void addLayer(TiledMapTileLayer layer, int posX, int posY, float rotation) {

        for(int x=0;x<layer.getWidth();x++) {
            for (int y = 0; y < layer.getHeight(); y++){
                collisionLayer.setCell(MathUtils.round(x + posX / layer.getTileWidth()), MathUtils.round(y + posY / layer.getTileHeight()), layer.getCell(x, y));
            }
        }

    }

    public void removeLayer(TiledMapTileLayer layer, int posX, int posY) {
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                if (layer.getCell(x, y) != null) {
                    collisionLayer.setCell(MathUtils.round(x + posX / getTileWidth()), MathUtils.round(y + posY / getTileHeight()), null);

                }
            }


        }
    }

    public boolean isPlaceable(TiledMapTileLayer layer, float posX, float posY) {
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                if (getTileInfo(x + posX, y + posY) != 0) {
                    return false;

                }
            }
        }
        return true;


    }


    public Integer getTileInfo(float x, float y) {
        TiledMapTileLayer.Cell cell = collisionLayer.getCell(MathUtils.round(x / collisionLayer.getTileWidth()), MathUtils.round(y / collisionLayer.getTileHeight()));

        if (cell == null || cell.getTile().getProperties().get("info", Integer.class) == null) {
            return NO_TILE;
        } else {
            return cell.getTile().getProperties().get("info", Integer.class);
        }

    }


    public float getWidth() {
        return collisionLayer.getWidth();
    }

    public float getHeight() {
        return collisionLayer.getHeight();
    }

    public float getTileWidth() {
        return collisionLayer.getTileWidth();
    }

    public float getTileHeight() {
        return collisionLayer.getTileHeight();
    }

}
