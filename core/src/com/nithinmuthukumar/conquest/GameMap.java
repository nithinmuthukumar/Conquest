package com.nithinmuthukumar.conquest;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import static com.nithinmuthukumar.conquest.Globals.NO_TILE;

public class GameMap {
    private TiledMapTileLayer collisionLayer;
    private Rectangle intersect = new Rectangle();

    public GameMap(int width, int height, int tileWidth, int tileHeight) {


        collisionLayer = new TiledMapTileLayer(width, height, tileWidth, tileHeight);

    }

    //need to add rotation here
    public void addLayer(TiledMapTileLayer layer, int posX, int posY) {

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
                if (getTileInfo(x * 16 + posX, y * 16 + posY) != 0) {
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

    public boolean isPassable(Vector2 trace, Vector2 target) {
        int minX = MathUtils.round(Math.min(trace.x, target.x));
        int maxX = MathUtils.round(Math.max(trace.x, target.x));
        int minY = MathUtils.round(Math.min(trace.y, target.y));
        int maxY = MathUtils.round(Math.max(trace.y, target.y));

        for (int x = minX - 1; x <= maxX + 1; x++) {
            for (int y = minY - 1; y <= maxY + 1; y++) {
                if (getTileInfo(x * 16, y * 16) == Globals.COLLIDE) {
                    if (Intersector.intersectSegmentRectangle(trace, target, intersect.set(x, y, 1, 1))) {

                        return false;
                    }
                }
            }
        }
        return true;
    }
}
