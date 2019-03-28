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
import com.nithinmuthukumar.conquest.*;
import com.nithinmuthukumar.conquest.Components.CameraComponent;
import com.nithinmuthukumar.conquest.Components.EnemyComponent;
import com.nithinmuthukumar.conquest.Components.PlayerComponent;
import com.nithinmuthukumar.conquest.Systems.*;
import com.nithinmuthukumar.conquest.UIs.BuildingUI;

import static com.nithinmuthukumar.conquest.Conquest.inputHandler;

public class PlayScreen implements Screen {
    private Conquest game;
    public Engine engine;
    private InputMultiplexer inputMultiplexer=new InputMultiplexer();
    private Stage stage;
    private PlayerController playerController;
    public PlayScreen(Conquest game){
        this.game=game;

        stage=new Stage();
        OrthographicCamera camera =new OrthographicCamera(960,720);

        CameraSystem cameraSystem=new CameraSystem(camera,game.batch);
        CollisionLayer collisionLayer=new CollisionLayer(200,200,16,16);
        engine=new Engine();
        EntityFactory.createBkg("backgrounds/world.png",engine);
        EntityFactory.createMap(0,0,"buildings/village_map",engine,collisionLayer);



        EntityFactory.createPlayer(engine,game.world);
        playerController=new PlayerController(engine.getEntitiesFor(Family.all(PlayerComponent.class).exclude(EnemyComponent.class).get()).first());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new RenderSystem(game.batch));
        engine.addSystem(new MapCollisionSystem(collisionLayer));
        engine.addSystem(new MovementSystem());
        engine.addSystem(cameraSystem);
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new RoofSystem());
        engine.addSystem(new PhysicsSystem(game.world));
        engine.addSystem(new DebugRenderSystem(game.world,camera));
        SocketSystem socketSystem=new SocketSystem();

        engine.addSystem(socketSystem);
        TextButton textButton=new TextButton("f", Assets.style);
        textButton.setPosition(0,0);
        textButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                EntityFactory.createMapNavigator(500,500,10,engine);
                for(Entity e:engine.getEntitiesFor(Family.all(CameraComponent.class).get())){
                    e.remove(CameraComponent.class);
                }
                playerController.off();
                engine.addSystem(new MouseFollowSystem());
                super.clicked(event, x, y);
            }
        });
        stage.addActor(textButton);
        new BuildingUI(stage);
        inputMultiplexer.addProcessor(inputHandler);
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
