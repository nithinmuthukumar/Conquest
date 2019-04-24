package com.nithinmuthukumar.conquest;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Components.Identifiers.BuiltComponent;
import com.nithinmuthukumar.conquest.Components.Identifiers.PlayerComponent;
import com.nithinmuthukumar.conquest.Components.Identifiers.RotatingComponent;
import com.nithinmuthukumar.conquest.Components.UIComponents.BuildingComponent;
import com.nithinmuthukumar.conquest.Components.SpawnerComponent;

public class Globals {

    public static final ComponentMapper<TransformComponent> transformComp = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<BodyComponent> bodyComp = ComponentMapper.getFor(BodyComponent.class);
    public static final ComponentMapper<StateComponent> stateComp = ComponentMapper.getFor(StateComponent.class);
    public static final ComponentMapper<VelocityComponent> velocityComp = ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<RenderableComponent> renderComp = ComponentMapper.getFor(RenderableComponent.class);
    public static final ComponentMapper<TargetComponent> targetComp = ComponentMapper.getFor(TargetComponent.class);
    public static final ComponentMapper<FighterComponent> fighterComp = ComponentMapper.getFor(FighterComponent.class);
    public static final ComponentMapper<HealthComponent> healthComp = ComponentMapper.getFor(HealthComponent.class);
    public static final ComponentMapper<CollisionRemoveComponent> collisionRemoveComp = ComponentMapper.getFor(CollisionRemoveComponent.class);
    public static final ComponentMapper<WeaponComponent> weaponComp = ComponentMapper.getFor(WeaponComponent.class);
    public static final ComponentMapper<RemovalComponent> removalComp = ComponentMapper.getFor(RemovalComponent.class);
    public static final ComponentMapper<RotatingComponent> rotatingComp = ComponentMapper.getFor(RotatingComponent.class);
    public static final ComponentMapper<ParticleComponent> particleComp = ComponentMapper.getFor(ParticleComponent.class);
    public static final ComponentMapper<SpawnerComponent> spawnComp = ComponentMapper.getFor(SpawnerComponent.class);
    public static final ComponentMapper<BuildingComponent> buildingComp = ComponentMapper.getFor(BuildingComponent.class);
    public static final ComponentMapper<BuiltComponent> builtComp = ComponentMapper.getFor(BuiltComponent.class);
    public static final ComponentMapper<PlayerComponent> playerComp = ComponentMapper.getFor(PlayerComponent.class);



    public static final int NO_TILE = 0;
    public static final int COLLIDE = 1;
    public static final int ELEVATE_COLLIDE = 4;
    public static final int INSIDE_HOUSE = 3;
    public static final int ELEVATE = 2;
    public static final int PLACEMENT_COLLIDE = 5;
    public static final int VERTICAL_MOVEMENT = 6;
    public static final int FLOOR_COLLIDE = 7;
    public static final int FOUR_DIRECTIONAL_MOVEMENT = 8;

    public static final int PPM = 100;
    public static final short BIT_PLAYER = 2;
    public static final short BIT_ENEMY = 4;
    public static final short BIT_PLAYERWEAPON = 8;
    public static final short BIT_ENEMYWEAPON = 16;
    public static final short BIT_ELEVATED = 32;
    public static final short BIT_ONGROUND = 64;




    public static final OrthographicCamera camera = new OrthographicCamera(960, 720);
    public static final World world = new World(new Vector2(), false);
    public static final SpriteBatch batch = new SpriteBatch();
    public static final PooledEngine engine = new PooledEngine();
    public static final InputHandler inputHandler = new InputHandler();
    public static final Player player = new Player();
    public static final BodyDef bodyDef=new BodyDef();
    public static final FixtureDef fixtureDef=new FixtureDef();
}