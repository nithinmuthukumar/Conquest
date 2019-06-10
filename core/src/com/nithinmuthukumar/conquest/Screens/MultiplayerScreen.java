package com.nithinmuthukumar.conquest.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Conquest;

public class MultiplayerScreen implements Screen {
    private Stage stage;


    public MultiplayerScreen() {

        stage = new Stage();
        TextButton readyButton = new TextButton("Ready", Assets.style);
        readyButton.setPosition(300, 300);
        readyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Conquest.client.getClient().sendTCP("ready");
                readyButton.remove();
                super.clicked(event, x, y);
            }
        });
        stage.addActor(readyButton);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);


    }


    @Override
    public void render(float delta) {
        stage.draw();

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
