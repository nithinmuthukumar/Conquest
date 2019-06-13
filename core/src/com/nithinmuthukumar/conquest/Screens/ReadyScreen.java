package com.nithinmuthukumar.conquest.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nithinmuthukumar.conquest.Assets;

public class ReadyScreen implements Screen {
    Stage stage = new Stage();
    @Override
    public void show() {
        TextButton readyButton = new TextButton("Ready", Assets.style);
        readyButton.addListener(new ClickListener() {

        });
        //stage.addActor();


    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
