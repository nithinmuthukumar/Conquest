package com.nithinmuthukumar.conquest.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.Helpers.Assets;
import javafx.scene.layout.Pane;

public class MenuScreen implements Screen {
    private Stage stage;
    private TextButton optionButton;
    private TextButton oneP;
    private TextButton multiP;
    public MenuScreen(Conquest game){
        TextButton playButton=new TextButton("Play", Assets.style);
        playButton.setPosition(500,500);
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.playScreen);
                super.clicked(event, x, y);
            }
        });

        playButton.addAction(Actions.fadeOut(2.5f));
        TextButton optionButton=new TextButton("Options",Assets.style);
        TextButton oneP=new TextButton("One Player",Assets.style);
        TextButton multiP=new TextButton("MultiPlayer",Assets.style);
        optionButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                optionButton.addAction(Actions.color(Color.BLACK));
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
