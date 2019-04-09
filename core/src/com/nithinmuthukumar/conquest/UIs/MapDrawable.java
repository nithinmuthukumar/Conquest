package com.nithinmuthukumar.conquest.UIs;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;
import com.nithinmuthukumar.conquest.GameMap;

public class MapDrawable implements TransformDrawable {

    private GameMap gameMap;
    private float zoom;

    public MapDrawable(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    @Override
    public void draw(Batch batch, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {

        draw(batch, x, y, width, height);

    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {

        batch.draw(gameMap.getBase(), x, y, width, height);
        float scaleW = width / gameMap.getBase().getWidth();
        float scaleH = height / gameMap.getBase().getHeight();
        for (Sprite s : gameMap.getImages()) {
            batch.draw(s.getTexture(), x + s.getX() * scaleW, y + s.getY() * scaleH, s.getWidth() * scaleW, s.getHeight() * scaleH);
        }


    }

    @Override
    public float getLeftWidth() {
        return 0;
    }

    @Override
    public void setLeftWidth(float leftWidth) {

    }

    @Override
    public float getRightWidth() {
        return 0;
    }

    @Override
    public void setRightWidth(float rightWidth) {

    }

    @Override
    public float getTopHeight() {
        return 0;
    }

    @Override
    public void setTopHeight(float topHeight) {

    }

    @Override
    public float getBottomHeight() {
        return 0;
    }

    @Override
    public void setBottomHeight(float bottomHeight) {

    }

    @Override
    public float getMinWidth() {
        return 0;
    }

    @Override
    public void setMinWidth(float minWidth) {

    }

    @Override
    public float getMinHeight() {
        return 0;
    }

    @Override
    public void setMinHeight(float minHeight) {

    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }


}
