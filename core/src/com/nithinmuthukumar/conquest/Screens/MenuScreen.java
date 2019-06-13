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

public class MenuScreen implements Screen {
    private Stage stage;
    private TextButton optionButton;
    private TextButton oneP;
    private TextButton multiP;

    public MenuScreen() {
        TextButton playButton=new TextButton("Play", Assets.style);
        playButton.setPosition(500,500);
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.selectionScreen);
                super.clicked(event, x, y);
            }
        });

        //playButton.addAction(Actions.fadeOut(2.5f));
        TextButton optionButton=new TextButton("Options",Assets.style);
        optionButton.setPosition(400, 400);
        optionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

            }
        });
        TextButton oneP=new TextButton("One Player",Assets.style);
        oneP.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ConquestServer.main(new String[]{});
                Globals.client.start();
                Globals.client.getClient().sendTCP("ready");


                super.clicked(event, x, y);
            }
        });
        oneP.setPosition(300, 300);
        TextButton multiP=new TextButton("MultiPlayer",Assets.style);
        multiP.setPosition(600, 100);
        multiP.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.multiplayerScreen);
                super.clicked(event, x, y);
            }
        });

        stage=new Stage();
        stage.addActor(optionButton);
        stage.addActor(oneP);
        stage.addActor(multiP);
        stage.addActor(playButton);


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act();

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
