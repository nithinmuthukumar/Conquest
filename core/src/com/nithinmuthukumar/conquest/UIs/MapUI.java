package com.nithinmuthukumar.conquest.UIs;

import com.badlogic.ashley.signals.Listener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.nithinmuthukumar.conquest.Assets;

public class MapUI extends Table {

    float zoom;
    private TextureRegion map;

    private float x, y;
    private Listener<TextureRegion> imageListener = (signal, img) -> {
        map = img;
        changeMapSize();
    };

    public MapUI(TextureRegionDrawable drawable) {
        super(Assets.style);
        zoom = 1;
        x = 0;
        y = 0;

        setPosition(Gdx.graphics.getWidth() / 2 - 250, Gdx.graphics.getHeight() / 2 - 250);
        map = drawable.getRegion();
        addListener(new ActorGestureListener() {
            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                super.pan(event, x, y, deltaX, deltaY);


            }


            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
            }
        });

        ImageButton zoomButton = new ImageButton(Assets.style, "add");
        zoomButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                zoom -= 0.1f;
                changeMapSize();
                super.clicked(event, x, y);
            }
        });
        ImageButton unzoomButton = new ImageButton(Assets.style, "sub");
        unzoomButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                zoom += 0.1f;
                changeMapSize();
                super.clicked(event, x, y);
            }
        });

        add(zoomButton).size(50, 50);
        add(unzoomButton).size(50, 50);
        setSize(500, 500);


    }

    public Listener getImageListener() {
        return imageListener;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }

    public void changeMapSize() {
        map.setRegion(MathUtils.round(x), MathUtils.round(y), MathUtils.round(map.getTexture().getWidth() * zoom), MathUtils.round(map.getTexture().getHeight() * zoom));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(map, 200, 200, 500, 500);
        super.draw(batch, parentAlpha);
    }
}
