package com.nithinmuthukumar.conquest.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.Assets;

public class SelectionScreen implements Screen {
    private Stage stage;

    public SelectionScreen(Conquest game){
        stage=new Stage();
        for(int i=0;i<3;i++){

        }
        TextButton campaignButton=new TextButton("campaign",Assets.style);
        campaignButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.playScreen);
                super.clicked(event, x, y);
            }
        });

        stage.addActor(campaignButton);



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
