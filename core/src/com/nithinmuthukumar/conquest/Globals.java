// =-=-=-=-=-=-=-= CONQUEST =-=-=-=-=-=-=-=
// Contains vars that we need to access from aanywhere

package com.nithinmuthukumar.conquest;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Systems.RenderManager;

public class Globals {
    //ComponentMappers which provide "lightning fast access to components" of the entities
    //they are some sort of hashmap which keeps track of all components created
    public static final ComponentMapper<TransformComponent> transformComp = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<BodyComponent> bodyComp = ComponentMapper.getFor(BodyComponent.class);
    public static final ComponentMapper<StateComponent> stateComp = ComponentMapper.getFor(StateComponent.class);
    public static final ComponentMapper<VelocityComponent> velocityComp = ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<RenderableComponent> renderComp = ComponentMapper.getFor(RenderableComponent.class);
    public static final ComponentMapper<TargetComponent> targetComp = ComponentMapper.getFor(TargetComponent.class);
    public static final ComponentMapper<AttackComponent> attackComp = ComponentMapper.getFor(AttackComponent.class);
    public static final ComponentMapper<HealthComponent> healthComp = ComponentMapper.getFor(HealthComponent.class);
    public static final ComponentMapper<CollisionRemoveComponent> collisionRemoveComp = ComponentMapper.getFor(CollisionRemoveComponent.class);
    public static final ComponentMapper<WeaponComponent> weaponComp = ComponentMapper.getFor(WeaponComponent.class);
    public static final ComponentMapper<RemovalComponent> removalComp = ComponentMapper.getFor(RemovalComponent.class);
    public static final ComponentMapper<ParticleComponent> particleComp = ComponentMapper.getFor(ParticleComponent.class);
    public static final ComponentMapper<SpawnerComponent> spawnerComp = ComponentMapper.getFor(SpawnerComponent.class);
    public static final ComponentMapper<BuiltComponent> builtComp = ComponentMapper.getFor(BuiltComponent.class);
    public static final ComponentMapper<PlayerComponent> playerComp = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<AnimationComponent> animationComp = ComponentMapper.getFor(AnimationComponent.class);
    public static final ComponentMapper<DecayComponent> decayComp = ComponentMapper.getFor(DecayComponent.class);
    public static final ComponentMapper<AllianceComponent> allianceComp = ComponentMapper.getFor(AllianceComponent.class);
    public static final ComponentMapper<AIComponent> aiComp = ComponentMapper.getFor(AIComponent.class);
    public static final ComponentMapper<EquipComponent> equipComp = ComponentMapper.getFor(EquipComponent.class);
    public static final ComponentMapper<EquippableComponent> equippableComp = ComponentMapper.getFor(EquippableComponent.class);
    public static final ComponentMapper<MeleeComponent> meleeComp = ComponentMapper.getFor(MeleeComponent.class);
    public static final ComponentMapper<DropComponent> dropComp = ComponentMapper.getFor(DropComponent.class);
    public static final ComponentMapper<FollowComponent> followComp = ComponentMapper.getFor(FollowComponent.class);
    public static final ComponentMapper<ShieldComponent> shieldComp = ComponentMapper.getFor(ShieldComponent.class);
    public static final ComponentMapper<ExplodeComponent> explodeComp = ComponentMapper.getFor(ExplodeComponent.class);
    public static final ComponentMapper<PoisonComponent> poisonComp = ComponentMapper.getFor(PoisonComponent.class);




    public static final String[] rarities = new String[]{"common", "rare", "epic"};

    //constant used for box2d pixel to meter conversions
    public static final int PPM = 100;
    public static final World world = new World(new Vector2(), false);
    public static final PooledEngine engine = new PooledEngine();
    public static final SpriteBatch batch = new SpriteBatch();


    public static OrthographicCamera camera;
    public static RenderManager renderSystem;

    public static GameMap gameMap;
    public static Player player;
    public static ConquestClient conquestClient;
    public static Conquest game;
    public static final String[] colors = new String[]{"White", "Red", "Blue", "Green", "Purple"};
}
