package com.nithinmuthukumar.conquest.Screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nithinmuthukumar.conquest.*;
import com.nithinmuthukumar.conquest.Components.CameraComponent;
import com.nithinmuthukumar.conquest.Components.PlayerComponent;
import com.nithinmuthukumar.conquest.Systems.*;
import com.nithinmuthukumar.conquest.UIs.BuildingUI;
import com.nithinmuthukumar.conquest.UIs.MapUI;

import static com.nithinmuthukumar.conquest.Conquest.inputHandler;
import static com.nithinmuthukumar.conquest.Constants.engine;

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


        OrthographicCamera camera =new OrthographicCamera(960,720);

        GameMap gameMap = new GameMap(200, 200, 16, 16, Assets.manager.get("backgrounds/world.png"));

        mapUI = new MapUI(new MapDrawable(gameMap));
        //player has to be created before playercontroller or the player controller will error
        EntityFactory.createPlayer();
        playerController = new PlayerController();
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new RenderSystem());
        engine.addSystem(new MapCollisionSystem(gameMap));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new CameraSystem(camera));
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new RoofSystem());
        engine.addSystem(new PhysicsSystem());
        engine.addSystem(new DebugRenderSystem(camera));
        engine.addSystem(new AISystem());
        engine.addSystem(new DirectionSystem());
        //SocketSystem socketSystem=new SocketSystem();

        //engine.addSystem(socketSystem);

        Image mapButton = new Image(new MapDrawable(gameMap));
        mapButton.setSize(300, 300);
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
        EntityFactory.createMap(600, 600, "buildings/village_map", gameMap);


        EntityFactory.createKnight(450, 450, engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).get(0));

        container.add(textButton);
        container.setSize(300, 300);

        stage.addActor(mapButton);
        BuildingUI buildingUI = new BuildingUI(stage, gameMap);

        stage.addActor(buildingUI.getPane());
        Gdx.input.vibrate(10000);
        inputHandler.addListener("keyUp", buildingUI.getListener());
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
