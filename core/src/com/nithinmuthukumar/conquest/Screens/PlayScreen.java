package com.nithinmuthukumar.conquest.Screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nithinmuthukumar.conquest.Components.CameraComponent;
import com.nithinmuthukumar.conquest.Components.PlayerComponent;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.GameMap;
import com.nithinmuthukumar.conquest.Helpers.Assets;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;
import com.nithinmuthukumar.conquest.Systems.*;
import com.nithinmuthukumar.conquest.UIs.BuildingUI;
import com.nithinmuthukumar.conquest.UIs.MapDrawable;
import com.nithinmuthukumar.conquest.UIs.MapUI;

import static com.nithinmuthukumar.conquest.Helpers.Globals.engine;
import static com.nithinmuthukumar.conquest.Helpers.Globals.inputHandler;

public class PlayScreen implements Screen {

    private InputMultiplexer inputMultiplexer=new InputMultiplexer();
    private Stage stage;
    private Table container;
    private PlayerController playerController;

    private MapUI mapUI;

    public PlayScreen(Conquest game){
        stage=new Stage();
        container = new Table();
        container.setPosition(0, 0);


        GameMap gameMap = new GameMap(200, 200, 16, 16, Assets.manager.get("backgrounds/world.png"));

        mapUI = new MapUI(new MapDrawable(gameMap));
        //player has to be created before playercontroller or the player controller will error
        EntityFactory.createPlayer();
        playerController = new PlayerController();
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new RenderSystem());
        engine.addSystem(new MapCollisionSystem(gameMap));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new CameraSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new RoofSystem());
        engine.addSystem(new PhysicsSystem());
        engine.addSystem(new DebugRenderSystem());
        engine.addSystem(new AISystem());
        engine.addSystem(new DirectionSystem());
        //SocketSystem socketSystem=new SocketSystem();

        //engine.addSystem(socketSystem);

        Image mapButton = new Image(new MapDrawable(gameMap));
        mapButton.setSize(250, 250);
        mapButton.setPosition(0, 0);
        mapButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playerController.off();
                stage.addActor(mapUI);
                super.clicked(event, x, y);
            }
        });

        TextButton textButton=new TextButton("f", Assets.style);
        textButton.setPosition(0,0);
        textButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                EntityFactory.createMapNavigator(500, 500, 10);
                for(Entity e:engine.getEntitiesFor(Family.all(CameraComponent.class).get())){
                    e.remove(CameraComponent.class);
                }
                playerController.off();
                engine.addSystem(new MouseFollowSystem());
                super.clicked(event, x, y);
            }
        });
        EntityFactory.createBkg("backgrounds/world.png");
        //EntityFactory.createMap(360, 360, "buildings/village_map", gameMap);


        EntityFactory.createKnight(450, 450, engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).get(0));

        container.add(textButton);
        container.setSize(300, 300);


        BuildingUI buildingUI = new BuildingUI(gameMap);
        Listener<Integer> keyUpListener = (signal, keycode) -> {
            if (keycode == Input.Keys.B) {
                playerController.flip();
                buildingUI.setVisible(!buildingUI.isVisible());
            }
        };
        inputHandler.addListener("keyUp", keyUpListener);

        stage.addActor(buildingUI);
        stage.addActor(mapButton);
        stage.addActor(container);

        container.debug();

        inputMultiplexer.addProcessor(inputHandler);
        inputMultiplexer.addProcessor(stage);


    }

    public void createGame() {

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
