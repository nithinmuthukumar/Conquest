package com.nithinmuthukumar.conquest;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

//holds all the collision tiles that are placed on the map
public class GameMap {
    //constants for tile info
    public static final int NO_TILE = 0;
    public static final int COLLIDE = 1;
    private TiledMapTileLayer collisionLayer;
    //the rectangle object which is used to check contains in isPassable
    private Rectangle intersect = new Rectangle();

    public GameMap(int width, int height, int tileWidth, int tileHeight) {


        collisionLayer = new TiledMapTileLayer(width, height, tileWidth, tileHeight);


    }

    //adds the given layer by looping through it's tiles and and placing the tile on the map
    public void addLayer(TiledMapTileLayer layer, int posX, int posY) {

        for(int x=0;x<layer.getWidth();x++) {
            for (int y = 0; y < layer.getHeight(); y++){
                collisionLayer.setCell(MathUtils.round(x + posX / layer.getTileWidth()), MathUtils.round(y + posY / layer.getTileHeight()), layer.getCell(x, y));
            }
        }

    }

    //goes through all the tiles in the given layer and makes the relative position on the collision layer null
    public void removeLayer(TiledMapTileLayer layer, int posX, int posY) {
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                if (layer.getCell(x, y) != null) {
                    collisionLayer.setCell(MathUtils.round(x + posX / getTileWidth()), MathUtils.round(y + posY / getTileHeight()), null);

                }
            }


        }
    }

    //checks if the layer is placeable at that position by checking whether there is another non null tile where the map is being placed
    public boolean isPlaceable(TiledMapTileLayer layer, float posX, float posY) {

        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                if (getTileInfo(x * 16 + posX, y * 16 + posY) != NO_TILE) {
                    return false;

                }
            }
        }
        return true;


    }

    //gets the tile info at the spot and returns 0 if there is no tile
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

    //method used for pathfinding
    public boolean isPassable(Vector2 trace, Vector2 target) {
        //the fields for the rectangle between trace and target
        int minX = MathUtils.round(Math.min(trace.x, target.x));
        int maxX = MathUtils.round(Math.max(trace.x, target.x));
        int minY = MathUtils.round(Math.min(trace.y, target.y));
        int maxY = MathUtils.round(Math.max(trace.y, target.y));
        //loops through the tiles between trace and its target
        for (int x = minX - 1; x <= maxX + 1; x++) {
            for (int y = minY - 1; y <= maxY + 1; y++) {
                //if its a collide tile check if the straight line through touches the rectangle
                if (getTileInfo(x * 16, y * 16) == COLLIDE) {
                    //if it does intersect it is not passable so return false
                    if (Intersector.intersectSegmentRectangle(trace, target, intersect.set(x, y, 1, 1))) {

                        return false;
                    }
                }
            }
        }
        return true;
    }
}
