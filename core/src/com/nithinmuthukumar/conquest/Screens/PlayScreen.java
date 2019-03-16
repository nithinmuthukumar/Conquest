package com.nithinmuthukumar.conquest.Screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.EntityFactory;
import com.nithinmuthukumar.conquest.Systems.*;

public class PlayScreen implements Screen {
    private Conquest game;
    public Engine engine;
    private PlayerInputSystem pis;
    public PlayScreen(Conquest game){
        this.game=game;
        OrthographicCamera camera =new OrthographicCamera(960,720);

        CameraSystem cameraSystem=new CameraSystem(camera,game.batch);
        engine=new Engine();
        EntityFactory.createMap(0,0,"village_map",engine,game.mapLoader);

        pis=new PlayerInputSystem();

        EntityFactory.createPlayer(engine);
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new RenderSystem(game.batch));
        engine.addSystem(new MapCollisionSystem());
        engine.addSystem(pis);
        engine.addSystem(new MovementSystem());
        engine.addSystem(cameraSystem);
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new RoofSystem());
        SocketSystem socketSystem=new SocketSystem();

        engine.addSystem(socketSystem);

    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(pis);

    }

    @Override
    public void render(float delta) {
        engine.update(Gdx.graphics.getDeltaTime());

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
