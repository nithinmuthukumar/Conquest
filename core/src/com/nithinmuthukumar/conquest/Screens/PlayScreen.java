package com.nithinmuthukumar.conquest.Screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.GameMap;
import com.nithinmuthukumar.conquest.Helpers.Assets;
import com.nithinmuthukumar.conquest.Helpers.B2DContactListener;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;
import com.nithinmuthukumar.conquest.Systems.*;
import com.nithinmuthukumar.conquest.UIs.BuildingUI;
import com.nithinmuthukumar.conquest.UIs.MapDrawable;
import com.nithinmuthukumar.conquest.UIs.MapUI;
import com.nithinmuthukumar.conquest.UIs.StatsUI;

import static com.nithinmuthukumar.conquest.Globals.*;

public class PlayScreen implements Screen {

    private InputMultiplexer inputMultiplexer=new InputMultiplexer();
    private Stage stage;

    private PlayerController playerController;

    private MapUI mapUI;
    private GameMap gameMap;

    public PlayScreen(){
        stage=new Stage();
        world.setContactListener(new B2DContactListener());
        //this is a custom filter which filters fixture using box2d's rules except
        // groupIndex is used to filter different z levels from colliding
        world.setContactFilter(new ContactFilter() {
            @Override
            public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
                Filter filterA = fixtureA.getFilterData();
                Filter filterB = fixtureB.getFilterData();
                //collision is only allowed if the z's are the same or the z is -1 which means it collides with everything
                if(filterA.groupIndex==filterB.groupIndex||filterA.groupIndex==-1||filterB.groupIndex==-1) {
                    //this statement is from the box2d docs
                    return (filterA.maskBits & filterB.categoryBits) != 0 && (filterA.categoryBits & filterB.maskBits) != 0;
                }
                return false;

            }
        });
        //this listener allow safe removal of box2d body
        //if not included the c++ code in box2d will error
        engine.addEntityListener(Family.all(RemovalComponent.class, BodyComponent.class).get(), new EntityListener() {
            @Override
            public void entityAdded(Entity entity) {
                entity.remove(BodyComponent.class);
            }
            @Override
            public void entityRemoved(Entity entity) { }
        });

        //creates the map for the game
        gameMap = new GameMap(200, 200, 16, 16, Assets.manager.get("backgrounds/world.png"));
        //mapUI controls the options when you choose the map
        mapUI = new MapUI(new MapDrawable(gameMap));




        //SocketSystem socketSystem=new SocketSystem();

        //engine.addSystem(socketSystem);
        //creates the mapButton

        //creates a mapButton in the bottom corner
        Image mapButton = new Image(new MapDrawable(gameMap));
        mapButton.setSize(250, 250);
        mapButton.setPosition(0, 0);
        mapButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //turns off player controls so that input does not go to player
                playerController.off();
                stage.addActor(mapUI);
                super.clicked(event, x, y);
            }
        });
        /*
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

         */






        //creates the building ui which allows player to pla
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


        inputMultiplexer.addProcessor(inputHandler);
        inputMultiplexer.addProcessor(stage);


    }


    @Override
    public void show() {
        EntityFactory.createBkg("backgrounds/world.png");

        EntityFactory.createPlayer();

        //adding systems to the engine
        playerController = new PlayerController();
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new MapSystem(gameMap));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new CameraSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new RoofSystem());
        engine.addSystem(new PhysicsSystem());
        engine.addSystem(new DebugRenderSystem());
        engine.addSystem(new AISystem());
        engine.addSystem(new DirectionSystem());
        engine.addSystem(new TargetFollowSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new HealthSystem());
        engine.addSystem(new RemovalSystem());
        engine.addSystem(new RenderManager());
        engine.addSystem(new StateParticleSystem());
        engine.addSystem(new SpawnSystem());
        stage.addActor(new StatsUI());
        generateMap();
        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    @Override
    public void render(float delta) {
        stage.act();
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
    public void generateMap(){


        for(int i=0;i<40;i++){
            int x=MathUtils.random((int)gameMap.getWidth()*16);
            int y=MathUtils.random((int)gameMap.getHeight()*16);
            BuildingData data=Assets.nonPlaceables.get("Tree");

            if (gameMap.isPlaceable(data, x , y)) {
                EntityFactory.createMap(data, x, y, gameMap);
            }else{
                System.out.println(true);
            }
        }
    }
}
