package com.nithinmuthukumar.conquest.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nithinmuthukumar.conquest.Conquest;

public class MenuScreen implements Screen {
    private Stage stage;
    private Conquest game;
    public MenuScreen(Conquest game){
        this.game=game;
        TextButton playButton=new TextButton("Play",game.style);
        playButton.setPosition(500,500);
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.playScreen);
                super.clicked(event, x, y);
            }
        });
        stage=new Stage();
        stage.addActor(playButton);
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
