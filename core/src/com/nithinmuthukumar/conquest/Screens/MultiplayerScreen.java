package com.nithinmuthukumar.conquest.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Server.ConquestServer;

public class MultiplayerScreen implements Screen {
    private Stage stage;


    public MultiplayerScreen() {

        stage = new Stage();
        TextButton createRoom = new TextButton("Create Room", Assets.style);
        createRoom.setPosition(300, 300);
        createRoom.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                createRoom.remove();
                ConquestServer.main(new String[]{});
                super.clicked(event, x, y);
            }
        });
        TextButton joinRoom = new TextButton("Join Room", Assets.style);

        stage.addActor(createRoom);

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
