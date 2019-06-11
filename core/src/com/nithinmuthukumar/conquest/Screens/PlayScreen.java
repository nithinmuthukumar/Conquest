package com.nithinmuthukumar.conquest.Screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Components.RemovalComponent;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.GameMap;
import com.nithinmuthukumar.conquest.Helpers.B2DContactListener;
import com.nithinmuthukumar.conquest.Systems.AI.*;
import com.nithinmuthukumar.conquest.Systems.*;
import com.nithinmuthukumar.conquest.Systems.UI.UISystem;

import static com.nithinmuthukumar.conquest.Globals.*;

public class PlayScreen implements Screen {

    private InputMultiplexer inputMultiplexer=new InputMultiplexer();

    private UISystem ui;



    @Override
    public void show() {
        Conquest.world.setContactListener(new B2DContactListener());
        //this is a custom filter which filters fixture using box2d's rules except
        // groupIndex is used to filter different z levels from colliding
        Conquest.world.setContactFilter((fixtureA, fixtureB) -> {

            Entity e1 = (Entity) fixtureA.getUserData();
            Entity e2 = (Entity) fixtureB.getUserData();
            if (shieldComp.has(e1) || shieldComp.has(e2)) {
                return allianceComp.get(e1) == allianceComp.get(e2);
            }

            if (Boolean.logicalXor(weaponComp.has(e1), weaponComp.has(e2))) {

                if (builtComp.has(e1) || builtComp.has(e2)) {
                    return false;
                }
                if (allianceComp.has(e1) && allianceComp.has(e2)) {
                    return allianceComp.get(e1) == allianceComp.get(e2);
                }

            }


            return true;

        });
        //this listener allow safe removal of box2d body
        //if not included the c++ code in box2d will error
        Conquest.engine.addEntityListener(Family.all(RemovalComponent.class, BodyComponent.class).get(), new EntityListener() {
            @Override
            public void entityAdded(Entity entity) {
                entity.remove(BodyComponent.class);
            }

            @Override
            public void entityRemoved(Entity entity) { }
        });
        ShapeRenderSystem shapeRenderSystem = new ShapeRenderSystem();

        ui = new UISystem(shapeRenderSystem);

        Conquest.gameMap = new GameMap(200, 200, 16, 16);
        Conquest.engine.addSystem(new AnimationSystem());
        //Conquest.engine.addSystem(new TileSystem(Conquest.gameMap));
        Conquest.engine.addSystem(new MovementSystem());
        Conquest.engine.addSystem(new CameraSystem());
        //Conquest.engine.addSystem(new RoofSystem());
        Conquest.engine.addSystem(new PhysicsSystem());
        Conquest.engine.addSystem(shapeRenderSystem);
        Conquest.engine.addSystem(new DirectionSystem());
        Conquest.engine.addSystem(new TargetSystem());
        Conquest.engine.addSystem(new CollisionSystem());
        Conquest.engine.addSystem(new DeathSystem());
        Conquest.engine.addSystem(new RemovalSystem());
        Conquest.engine.addSystem(new RenderManager());
        Conquest.engine.addSystem(new StateParticleSystem());
        Conquest.engine.addSystem(new SpawnSystem());
        Conquest.engine.addSystem(ui);
        Conquest.engine.addSystem(new DecaySystem());
        Conquest.engine.addSystem(new TowerAI());
        Conquest.engine.addSystem(new ShooterAI());
        Conquest.engine.addSystem(new MeleeAI());
        Conquest.engine.addSystem(new SpawnerAI());
        Conquest.engine.addSystem(new PathFindingSystem());
        Conquest.engine.addSystem(new FollowAI());
        //Conquest.engine.addSystem(new DeathMatchSystem());
        //generateMap();


        setupGame();

        //adding systems to the engine

        inputMultiplexer.addProcessor(Conquest.client.getInputHandler());
        inputMultiplexer.addProcessor(ui.getStage());

        Gdx.input.setInputProcessor(inputMultiplexer);


    }

    public void setupGame(){
        Entity ground=Assets.recipes.get("ground").make();
        Conquest.engine.addEntity(ground);




    }

    @Override
    public void render(float delta) {
        Conquest.engine.update(Gdx.graphics.getDeltaTime());




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
