package com.nithinmuthukumar.conquest.Screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.physics.box2d.*;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Components.RemovalComponent;
import com.nithinmuthukumar.conquest.GameMap;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;
import com.nithinmuthukumar.conquest.Systems.AI.*;
import com.nithinmuthukumar.conquest.Systems.*;
import com.nithinmuthukumar.conquest.Systems.UI.UISystem;

import static com.nithinmuthukumar.conquest.Globals.*;

public class PlayScreen implements Screen {

    private InputMultiplexer inputMultiplexer=new InputMultiplexer();

    private UISystem ui;



    @Override
    public void show() {
        Music music = Gdx.audio.newMusic(Gdx.files.internal("Awakening.ogg"));
        music.play();
        music.setLooping(true);

        world.setContactListener(new ContactListener() {
            //sets the collided entity of each entity
            @Override
            public void beginContact(Contact contact) {
                //the user data will always be an entity
                Entity a = (Entity) contact.getFixtureA().getUserData();
                Entity b = (Entity) contact.getFixtureB().getUserData();
                //set the collided entity of either entity
                if (a == null || b == null) {
                    return;
                }

                bodyComp.get(a).collidedEntities.addLast(b);
                bodyComp.get(b).collidedEntities.addLast(a);
            }

            //if the entity stops colliding the body simply removes it from the collided entities array
            @Override
            public void endContact(Contact contact) {
                Entity a = (Entity) contact.getFixtureA().getUserData();
                Entity b = (Entity) contact.getFixtureB().getUserData();
                if (a == null || b == null) {
                    return;
                }
                //if it doesn't have a BodyComponent, it was probably removed
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
        //this determines the rules between whether two bodies should collide
        world.setContactFilter((fixtureA, fixtureB) -> {

            Entity e1 = (Entity) fixtureA.getUserData();
            Entity e2 = (Entity) fixtureB.getUserData();
            //if its a shield it collides with anything that is an enemy
            if (e1 == null || e2 == null) {
                return true;
            }
            if (shieldComp.has(e1) || shieldComp.has(e2)) {
                if (allianceComp.has(e1) && allianceComp.has(e2)) {
                    return allianceComp.get(e1).side != allianceComp.get(e2).side;
                }
            }
            //a weapon collides with everything but other weapons
            if (Boolean.logicalXor(weaponComp.has(e1), weaponComp.has(e2))) {

                //if they are on opposing side collision is allowed
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
        //the creation of all the systems
        ui = new UISystem(shapeRenderSystem);
        //creates a new gameMap which is just a collision layer
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
        //Conquest.engine.addSystem(new SandBoxSystem());
        //generateMap();


        setupGame();

        //adding input processors to multiplexer
        inputMultiplexer.addProcessor(conquestClient.getInputHandler());
        inputMultiplexer.addProcessor(ui.getStage());

        Gdx.input.setInputProcessor(inputMultiplexer);


    }

    //creates the ground for the game
    public void setupGame(){
        Body boundary = EntityFactory.bodyBuilder("StaticBody", 0, 0);
        EntityFactory.createRectFixture(3200, 0, 3200, 25, false, null, boundary, 1, 1);
        EntityFactory.createRectFixture(3200, 0, 25, 3200, false, null, boundary, 1, 1);
        EntityFactory.createRectFixture(0, 3200, 3200, 25, false, null, boundary, 1, 1);
        EntityFactory.createRectFixture(0, 3200, 25, 3200, false, null, boundary, 1, 1);
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
}
