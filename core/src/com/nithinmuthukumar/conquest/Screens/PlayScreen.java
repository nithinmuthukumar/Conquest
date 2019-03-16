package com.nithinmuthukumar.conquest.Screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nithinmuthukumar.conquest.Components.CameraComponent;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.EntityFactory;
import com.nithinmuthukumar.conquest.Systems.*;

public class PlayScreen implements Screen {
    private Conquest game;
    public Engine engine;
    private PlayerInputSystem pis;
    private InputMultiplexer inputMultiplexer=new InputMultiplexer();
    private Stage stage;
    public PlayScreen(Conquest game){
        this.game=game;
        stage=new Stage();
        OrthographicCamera camera =new OrthographicCamera(960,720);

        CameraSystem cameraSystem=new CameraSystem(camera,game.batch);
        engine=new Engine();
        EntityFactory.createMap(0,0,"village_map",engine,game.mapLoader);

        pis=new PlayerInputSystem();
        inputMultiplexer.addProcessor(pis);

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
        TextButton textButton=new TextButton("f",game.style);
        textButton.setPosition(0,0);
        textButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                EntityFactory.createMapNavigator(500,500,10,engine);
                for(Entity e:engine.getEntitiesFor(Family.all(CameraComponent.class).get())){
                    e.remove(CameraComponent.class);
                }
                inputMultiplexer.removeProcessor(pis);
                engine.addSystem(new MouseFollowSystem());
                super.clicked(event, x, y);
            }
        });
        stage.addActor(textButton);
        inputMultiplexer.addProcessor(stage);


    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    @Override
    public void render(float delta) {
        engine.update(Gdx.graphics.getDeltaTime());
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
