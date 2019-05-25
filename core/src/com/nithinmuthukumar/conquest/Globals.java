package com.nithinmuthukumar.conquest;

import com.badlogic.ashley.core.ComponentMapper;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Components.Identifiers.*;

public class Globals {

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
    public static final ComponentMapper<RotatingComponent> rotatingComp = ComponentMapper.getFor(RotatingComponent.class);
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


}
