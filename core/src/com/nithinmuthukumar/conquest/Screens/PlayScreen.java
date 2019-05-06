package com.nithinmuthukumar.conquest.Screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Transform;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.AIComponent;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Components.Identifiers.EnemyComponent;
import com.nithinmuthukumar.conquest.Components.RemovalComponent;
import com.nithinmuthukumar.conquest.GameMap;
import com.nithinmuthukumar.conquest.Helpers.B2DContactListener;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;
import com.nithinmuthukumar.conquest.Helpers.Utils;
import com.nithinmuthukumar.conquest.Player;
import com.nithinmuthukumar.conquest.Systems.*;
import com.nithinmuthukumar.conquest.Systems.UI.UISystem;

import static com.nithinmuthukumar.conquest.Globals.*;

public class PlayScreen implements Screen {

    private InputMultiplexer inputMultiplexer=new InputMultiplexer();

    private PlayerController playerController;


    private GameMap gameMap;

    public PlayScreen(){
        world.setContactListener(new B2DContactListener());
        //this is a custom filter which filters fixture using box2d's rules except
        // groupIndex is used to filter different z levels from colliding
        world.setContactFilter((fixtureA, fixtureB) -> {
            Filter filterA = fixtureA.getFilterData();
            Filter filterB = fixtureB.getFilterData();
            //collision is only allowed if the z's are the same or the z is -1 which means it collides with everything
            if (filterA.groupIndex == filterB.groupIndex || filterA.groupIndex == -1 || filterB.groupIndex == -1) {
                //this statement is from the box2d docs
                return (filterA.maskBits & filterB.categoryBits) != 0 && (filterA.categoryBits & filterB.maskBits) != 0;
            }
            return false;

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






        gameMap = new GameMap(200, 200, 16, 16);
        setupGame();

        //adding systems to the engine
        playerController = new PlayerController();
        UISystem ui=new UISystem(gameMap,playerController);
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new TileSystem(gameMap));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new CameraSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new RoofSystem());
        engine.addSystem(new PhysicsSystem());
        engine.addSystem(new DebugRenderSystem(gameMap));
        engine.addSystem(new DirectionSystem());
        engine.addSystem(new TargetFollowSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new HealthSystem());
        engine.addSystem(new RemovalSystem());
        engine.addSystem(new RenderManager());
        engine.addSystem(new StateParticleSystem());
        engine.addSystem(new SpawnSystem());
        engine.addSystem(ui);
        engine.addSystem(new DecaySystem());
        engine.addSystem(new TowerSystem());
        engine.addSystem(new AISystem());
        //generateMap();
        inputMultiplexer.addProcessor(inputHandler);
        inputMultiplexer.addProcessor(ui.getStage());
        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    public void setupGame(){
        Entity ground=Assets.recipes.get("ground").make();
        engine.addEntity(ground);

        player = new Player(Assets.recipes.get("player").make());

        placeRandomly(player.getEntity());
        Entity item = EntityFactory.createItem(Assets.itemDatas.get("villager sword"));
        BodyComponent body = bodyComp.get(item);
        Transform t = bodyComp.get(player.getEntity()).body.getTransform();
        body.body.setTransform(t.getPosition().x + 200, t.getPosition().y, 0);
        engine.addEntity(item);

        for (int i = 0; i < 10; i++) {
            EntityFactory.createBuilding(
                    MathUtils.random(3200), MathUtils.random(3200),
                    Assets.buildingDatas.get("barracks"), gameMap).add(engine.createComponent(EnemyComponent.class)).add(engine.createComponent(AIComponent.class));

        }


    }

    public void placeRandomly(Entity e) {
        BodyComponent body = bodyComp.get(e);
        body.body.setTransform(MathUtils.random(200 * 16), MathUtils.random(200 * 16), body.body.getAngle());
        Utils.setUserData(e);
        engine.addEntity(e);


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
