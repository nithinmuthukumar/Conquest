// =-=-=-=-=-=-=-= CONQUEST =-=-=-=-=-=-=-=
//Lets a user create a lobby, starting the multiplayer game
//User also has the option ot join a pre-existing game

package com.nithinmuthukumar.conquest.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Globals;
import com.nithinmuthukumar.conquest.Server.ConquestServer;

import static com.nithinmuthukumar.conquest.Globals.game;

public class MultiplayerScreen implements Screen {
    private Stage stage;


    public MultiplayerScreen() {
        TextButton backButton = new TextButton("back", Assets.style);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen());
                super.clicked(event, x, y);
            }
        });
        backButton.setPosition(700, 650);

        stage = new Stage();
        stage.addActor(backButton);
        //the difference between join room and create room is that create room runs a server and join room doesn't
        TextButton readyButton = new TextButton("Start", Assets.style);
        readyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Globals.conquestClient.getClient().sendTCP("ready");
                super.clicked(event, x, y);
            }
        });
        readyButton.setPosition(200, 200);
        TextButton createRoom = new TextButton("Create Room", Assets.style);
        TextButton joinRoom = new TextButton("Join Room", Assets.style);
        createRoom.setPosition(300, 300);
        createRoom.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ConquestServer.main(new String[]{});
                Globals.conquestClient.start();
                stage.addActor(readyButton);
                joinRoom.remove();
                createRoom.remove();

                super.clicked(event, x, y);
            }
        });

        joinRoom.setPosition(450, 300);
        joinRoom.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                createRoom.remove();
                joinRoom.remove();
                Globals.conquestClient.start();
            }
        });

        stage.addActor(joinRoom);
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
