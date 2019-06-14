// =-=-=-=-=-=-=-= CONQUEST =-=-=-=-=-=-=-=
//Simple screen that shows the controls and keyboard bindings

package com.nithinmuthukumar.conquest.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.nithinmuthukumar.conquest.Assets;

import static com.nithinmuthukumar.conquest.Globals.game;

public class InstructionScreen implements Screen {
    private Stage stage;

    @Override
    public void show() {
        stage = new Stage();
        TextButton backButton = new TextButton("back", Assets.style);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen());
                super.clicked(event, x, y);
            }
        });
        Table table = new Table();
        table.add(new Label("Controls", Assets.style)).row();
        table.add(new Image(new TextureRegionDrawable(Assets.style.getRegion("divider_18"))));
        table.add(new Label("R ----------- Walk", Assets.style)).row();
        table.add(new Image(new TextureRegionDrawable(Assets.style.getRegion("divider_60"))));
        table.add(new Label("A ----------- Pick Up", Assets.style)).row();
        table.add(new Image(new TextureRegionDrawable(Assets.style.getRegion("divider_56"))));
        table.add(new Label("1 ----------- Sword", Assets.style)).row();
        table.add(new Image(new TextureRegionDrawable(Assets.style.getRegion("divider_10"))));
        table.add(new Label("2 ----------- Bow", Assets.style)).row();
        table.add(new Image(new TextureRegionDrawable(Assets.style.getRegion("divider_12"))));
        table.add(new Label("3 ----------- Bomb", Assets.style)).row();
        table.add(new Image(new TextureRegionDrawable(Assets.style.getRegion("divider_94"))));
        table.add(new Label("4 ----------- Shield", Assets.style)).row();
        table.add(new Image(new TextureRegionDrawable(Assets.style.getRegion("divider_96"))));
        table.add(new Label("S ----------- Spawn", Assets.style)).row();
        table.add(new Image(new TextureRegionDrawable(Assets.style.getRegion("divider_92"))));
        table.add(new Label("M ----------- Map", Assets.style)).row();
        table.add(new Image(new TextureRegionDrawable(Assets.style.getRegion("divider_84"))));
        table.add(new Label("I ----------- Inventory", Assets.style)).row();
        table.add(new Image(new TextureRegionDrawable(Assets.style.getRegion("divider_70"))));
        table.add(new Label("O ----------- Shop", Assets.style)).row();
        table.add(new Image(new TextureRegionDrawable(Assets.style.getRegion("divider_74"))));
        table.add(new Label("B ----------- Build", Assets.style)).row();


        backButton.setPosition(700, 650);
        stage.addActor(backButton);
        stage.addActor(table);
        table.setPosition(400, 500);

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
