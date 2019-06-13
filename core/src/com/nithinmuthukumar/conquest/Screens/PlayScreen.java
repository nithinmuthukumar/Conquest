package com.nithinmuthukumar.conquest.Screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Components.RemovalComponent;
import com.nithinmuthukumar.conquest.GameMap;
import com.nithinmuthukumar.conquest.Systems.AI.*;
import com.nithinmuthukumar.conquest.Systems.*;
import com.nithinmuthukumar.conquest.Systems.UI.UISystem;

import static com.nithinmuthukumar.conquest.Globals.*;

public class PlayScreen implements Screen {

    private InputMultiplexer inputMultiplexer=new InputMultiplexer();

    private UISystem ui;



    @Override
    public void show() {
        world.setContactListener(new ContactListener() {
            //sets the collided entity of each entity
            @Override
            public void beginContact(Contact contact) {
                Entity a = (Entity) contact.getFixtureA().getUserData();
                Entity b = (Entity) contact.getFixtureB().getUserData();

                bodyComp.get(a).collidedEntities.addLast(b);
                bodyComp.get(b).collidedEntities.addLast(a);
            }

            @Override
            public void endContact(Contact contact) {
                Entity a = (Entity) contact.getFixtureA().getUserData();
                Entity b = (Entity) contact.getFixtureB().getUserData();
                if (bodyComp.has(a)) bodyComp.get(a).collidedEntities.removeValue(b, true);
                if (bodyComp.has(b)) bodyComp.get(b).collidedEntities.removeValue(a, true);
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });
        //this is a custom filter which filters fixture using box2d's rules except
        // groupIndex is used to filter different z levels from colliding
        world.setContactFilter((fixtureA, fixtureB) -> {

            Entity e1 = (Entity) fixtureA.getUserData();
            Entity e2 = (Entity) fixtureB.getUserData();
            if (shieldComp.has(e1) || shieldComp.has(e2)) {
                return allianceComp.get(e1).side != allianceComp.get(e2).side;
            }

            if (Boolean.logicalXor(weaponComp.has(e1), weaponComp.has(e2))) {


                if (allianceComp.has(e1) && allianceComp.has(e2)) {
                    return allianceComp.get(e1).side != allianceComp.get(e2).side;
                }

            }


            return true;

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
        ShapeRenderSystem shapeRenderSystem = new ShapeRenderSystem();

        ui = new UISystem(shapeRenderSystem);

        gameMap = new GameMap(200, 200, 16, 16);
        renderSystem = new RenderManager();
        engine.addSystem(new AnimationSystem());
        //Conquest.engine.addSystem(new TileSystem(Conquest.gameMap));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new CameraSystem());
        //Conquest.engine.addSystem(new RoofSystem());
        engine.addSystem(new PhysicsSystem());
        engine.addSystem(shapeRenderSystem);
        engine.addSystem(new DirectionSystem());
        engine.addSystem(new TargetSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new DeathSystem());
        engine.addSystem(new RemovalSystem());
        engine.addSystem(renderSystem);
        engine.addSystem(new StateParticleSystem());
        engine.addSystem(new SpawnSystem());
        engine.addSystem(ui);
        engine.addSystem(new DecaySystem());
        engine.addSystem(new TowerAI());
        engine.addSystem(new ShooterAI());
        engine.addSystem(new MeleeAI());
        engine.addSystem(new SpawnerAI());
        engine.addSystem(new PathFindingSystem());
        engine.addSystem(new FollowAI());
        //Conquest.engine.addSystem(new DeathMatchSystem());
        //generateMap();


        setupGame();

        //adding systems to the engine

        inputMultiplexer.addProcessor(client.getInputHandler());
        inputMultiplexer.addProcessor(ui.getStage());

        Gdx.input.setInputProcessor(inputMultiplexer);


    }

    public void setupGame(){
        Entity ground=Assets.recipes.get("ground").make();
        engine.addEntity(ground);




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
