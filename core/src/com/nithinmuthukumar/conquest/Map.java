package com.nithinmuthukumar.conquest;

import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import static com.nithinmuthukumar.conquest.Utils.NO_TILE;

public class Map {
    private TiledMapTileLayer collisionLayer;
    /*holds images that will be put together to create the map
    this makes removal of buildings possible
     */
    private Array<Sprite> images;
    private Texture base;
    private Signal<TextureRegion> imageSignal = new Signal<>();

    public Map(int width, int height, int tileWidth, int tileHeight, Texture base) {
        images = new Array<>();

        collisionLayer = new TiledMapTileLayer(width, height, tileWidth, tileHeight);
        this.base = base;

    }

    //need to add rotation here
    public void addLayer(TiledMapTileLayer layer, int posX, int posY, Texture texture, float rotation) {
        for(int x=0;x<layer.getWidth();x++) {
            for (int y = 0; y < layer.getHeight(); y++){
                collisionLayer.setCell(MathUtils.round(x + posX / layer.getTileWidth()), MathUtils.round(y + posY / layer.getTileHeight()), layer.getCell(x, y));
            }
        }
        Sprite img = new Sprite(texture);
        img.setPosition(posX, posY);
        images.add(img);
        imageSignal.dispatch(getImage());

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
        if (cell == null) {
            return NO_TILE;
        } else {
            return cell.getTile().getProperties().get("info", Integer.class);
        }

    }

    public TextureRegion getImage() {
        Pixmap pixels = new Pixmap(base.getWidth(), base.getHeight(), base.getTextureData().getFormat());
        base.getTextureData().prepare();
        pixels.drawPixmap(base.getTextureData().consumePixmap(), 0, 0);


        for (Sprite s : images) {
            s.getTexture().getTextureData().prepare();
            pixels.drawPixmap(s.getTexture().getTextureData().consumePixmap(), MathUtils.round(s.getX()), MathUtils.round(s.getY()));

        }

        return new TextureRegion(new Texture(pixels));


    }

    public void addImageListener(Listener listener) {
        imageSignal.add(listener);
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
