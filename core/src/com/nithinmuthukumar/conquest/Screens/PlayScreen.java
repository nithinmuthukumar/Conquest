package com.nithinmuthukumar.conquest.Screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.nithinmuthukumar.conquest.*;
import com.nithinmuthukumar.conquest.Components.CameraComponent;
import com.nithinmuthukumar.conquest.Components.EnemyComponent;
import com.nithinmuthukumar.conquest.Components.PlayerComponent;
import com.nithinmuthukumar.conquest.Systems.*;
import com.nithinmuthukumar.conquest.UIs.BuildingUI;

import static com.nithinmuthukumar.conquest.Conquest.inputHandler;

public class PlayScreen implements Screen {
    public Engine engine;
    private InputMultiplexer inputMultiplexer=new InputMultiplexer();
    private Stage stage;
    private PlayerController playerController;
    private ImageButton mapButton;
    private BuildingUI buildingUI;

    public PlayScreen(Conquest game){
        stage=new Stage();


        OrthographicCamera camera =new OrthographicCamera(960,720);

        CameraSystem cameraSystem=new CameraSystem(camera,game.batch);
        Map map = new Map(200, 200, 16, 16, Assets.manager.get("backgrounds/world.png"));

        engine=new Engine();
        EntityFactory.createBkg("backgrounds/world.png",engine);

        TextureRegionDrawable drawable = new TextureRegionDrawable(map.getImage());
        buildingUI = new BuildingUI(drawable);
        map.addImageListener(buildingUI.getImageListener());


        mapButton = new ImageButton(drawable);
        mapButton.setSize(200, 200);
        Listener<TextureRegion> mapListener = (signal, object) -> {

            mapButton.getImage().remove();
            TextureRegion img = new TextureRegion(Utils.resizeTexture(object.getTexture(), 200, 200));
            mapButton.add(new Image(new TextureRegionDrawable(img)));
        };
        map.addImageListener(mapListener);

        mapButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playerController.off();
                stage.addActor(buildingUI);
                super.clicked(event, x, y);
            }
        });
        EntityFactory.createMap(600, 600, "buildings/village_map", engine, map);




        EntityFactory.createPlayer(engine,game.world);
        EntityFactory.createKnight(engine, game.world, 450, 450, engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).get(0));
        playerController=new PlayerController(engine.getEntitiesFor(Family.all(PlayerComponent.class).exclude(EnemyComponent.class).get()).first());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new RenderSystem(game.batch));
        engine.addSystem(new MapCollisionSystem(map));
        engine.addSystem(new MovementSystem());
        engine.addSystem(cameraSystem);
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new RoofSystem());
        engine.addSystem(new PhysicsSystem(game.world));
        engine.addSystem(new DebugRenderSystem(game.world,camera));
        engine.addSystem(new AISystem());
        engine.addSystem(new DirectionSystem());
        //SocketSystem socketSystem=new SocketSystem();

        //engine.addSystem(socketSystem);
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
        stage.addActor(mapButton);

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
