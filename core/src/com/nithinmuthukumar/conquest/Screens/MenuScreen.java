package com.nithinmuthukumar.conquest.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Globals;
import com.nithinmuthukumar.conquest.Server.ConquestServer;
import com.nithinmuthukumar.conquest.Systems.GameModes.SandBoxSystem;
import com.rafaskoberg.gdx.typinglabel.TypingLabel;

import static com.nithinmuthukumar.conquest.Globals.engine;
import static com.nithinmuthukumar.conquest.Globals.game;

//shows different options and changes screens when a button is clicked
public class MenuScreen implements Screen {
    private Stage stage;


    public MenuScreen() {
        TypingLabel title = new TypingLabel("{EASE}CONQUEST", Assets.style);
        title.setColor(Color.LIGHT_GRAY);
        title.setFontScale(1.5f);
        title.setPosition(400, 650);
        TextButton instructions = new TextButton("Instructions", Assets.style);
        instructions.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new InstructionScreen());
            }
        });
        instructions.setPosition(350, 400);


        TextButton oneP=new TextButton("One Player",Assets.style);
        oneP.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ConquestServer.main(new String[]{});
                Globals.conquestClient.start();
                Globals.conquestClient.getClient().sendTCP("one player");
                engine.addSystem(new SandBoxSystem());

                super.clicked(event, x, y);
            }
        });
        oneP.setPosition(250, 300);
        TextButton multiP=new TextButton("MultiPlayer",Assets.style);
        multiP.setPosition(550, 300);
        multiP.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MultiplayerScreen());
                super.clicked(event, x, y);
            }
        });

        stage=new Stage();
        stage.addActor(instructions);
        stage.addActor(oneP);
        stage.addActor(multiP);
        stage.addActor(title);


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
