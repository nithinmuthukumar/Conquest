// =-=-=-=-=-=-=-= CONQUEST =-=-=-=-=-=-=-=
// Displays a motivational message when the user dies

package com.nithinmuthukumar.conquest.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.nithinmuthukumar.conquest.Assets;
import com.rafaskoberg.gdx.typinglabel.TypingLabel;

public class GameOverScreen implements Screen {
    Stage stage = new Stage();
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        TypingLabel label = new TypingLabel("{EASE}You died. Loser\n {SLOW}Created by: Nithin", Assets.style);
        label.setPosition(400, 500);
        stage.addActor(label);

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
