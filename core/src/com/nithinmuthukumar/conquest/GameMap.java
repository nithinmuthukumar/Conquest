package com.nithinmuthukumar.conquest;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import static com.nithinmuthukumar.conquest.Helpers.Globals.NO_TILE;

public class GameMap {
    private TiledMapTileLayer collisionLayer;
    /*holds images that will be put together to create the map
    this makes removal of buildings possible
     */
    private Array<Sprite> images;
    private Texture base;

    public GameMap(int width, int height, int tileWidth, int tileHeight, Texture base) {
        images = new Array<>();


        collisionLayer = new TiledMapTileLayer(width, height, tileWidth, tileHeight);
        this.base = base;

    }

    //need to add rotation here
    public void addLayer(TiledMapTileLayer layer, int posX, int posY, Texture texture, float rotation) {
        posX = (posX / 16) * 16;
        posY = (posY / 16) * 16;
        for(int x=0;x<layer.getWidth();x++) {
            for (int y = 0; y < layer.getHeight(); y++){
                collisionLayer.setCell(MathUtils.round(x + posX / layer.getTileWidth()), MathUtils.round(y + posY / layer.getTileHeight()), layer.getCell(x, y));
            }
        }
        Sprite img = new Sprite(texture);
        img.setPosition(posX, posY);
        images.add(img);

    }

    public boolean isPlaceable(TiledMapTileLayer layer, float posX, float posY) {
        for (int y = 0; y < posY / getTileHeight(); y++) {
            for (int x = 0; x < layer.getWidth() / getTileWidth(); x++) {
                if (getTileInfo(posX + x, posY + y) != 0) {
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

    public Array<Sprite> getImages() {
        return images;
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

    public Texture getBase() {
        return base;
    }
}
