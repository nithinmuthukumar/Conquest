package com.nithinmuthukumar.conquest.Screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.GameMap;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Helpers.B2DContactListener;
import com.nithinmuthukumar.conquest.Systems.*;
import com.nithinmuthukumar.conquest.Systems.UI.UISystem;
import com.nithinmuthukumar.conquest.UIs.MapUI;
import com.nithinmuthukumar.conquest.UIs.StatsUI;

import static com.nithinmuthukumar.conquest.Globals.*;

public class PlayScreen implements Screen {

    private InputMultiplexer inputMultiplexer=new InputMultiplexer();

    private PlayerController playerController;

    private MapUI mapUI;
    private GameMap gameMap;

    public PlayScreen(){
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

        //mapUI controls the options when you choose the map
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














    }


    @Override
    public void show() {
        engine.addEntity(Assets.recipes.get("player").make());
        engine.addEntity(Assets.recipes.get("ground").make());
        gameMap = new GameMap(200, 200, 16, 16);

        //adding systems to the engine
        playerController = new PlayerController();
        UISystem ui=new UISystem(gameMap,playerController);
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
        engine.addSystem(ui);
        //generateMap();
        inputMultiplexer.addProcessor(inputHandler);
        //inputMultiplexer.addProcessor(ui.getStage());
        Gdx.input.setInputProcessor(inputMultiplexer);

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
    /*
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

     */
}
