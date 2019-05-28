package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Components.Identifiers.AllianceComponent;
import com.nithinmuthukumar.conquest.Components.Identifiers.MeleeComponent;
import com.nithinmuthukumar.conquest.Components.Identifiers.ShooterComponent;
import com.nithinmuthukumar.conquest.Components.Identifiers.TowerComponent;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Globals;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;
import com.nithinmuthukumar.conquest.Helpers.SpawnNode;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import java.util.Arrays;

import static com.nithinmuthukumar.conquest.Globals.*;


public class AISystem extends EntitySystem {
    private ImmutableArray<Entity> shooters;
    private ImmutableArray<Entity> melee;
    private ImmutableArray<Entity> spawners;
    private ImmutableArray<Entity> towers;

    public AISystem() {
        super(6);

    }

    @Override
    public void addedToEngine(Engine engine) {
        //the different groups which all have different ai logic
        shooters = engine.getEntitiesFor(Family.all(ShooterComponent.class, AIComponent.class).exclude(RemovalComponent.class).get());
        melee = engine.getEntitiesFor(Family.all(MeleeComponent.class, AIComponent.class).exclude(RemovalComponent.class).get());
        spawners = engine.getEntitiesFor(Family.all(SpawnerComponent.class, AIComponent.class).exclude(RemovalComponent.class).get());
        towers = engine.getEntitiesFor(Family.all(TowerComponent.class, AIComponent.class).exclude(RemovalComponent.class).get());
        super.addedToEngine(engine);
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : shooters) {
            TransformComponent transform = transformComp.get(entity);
            AIComponent ai = aiComp.get(entity);
            StateComponent state = stateComp.get(entity);
            AnimationComponent ani = animationComp.get(entity);
            FollowComponent follow = followComp.get(entity);
            TargetComponent target = targetComp.get(entity);

            if (follow.target == null) {
                state.action = Action.IDLE;
                findTarget(ai, transform, follow, entity);

                continue;
            }
            if (transform.dst(target.getPos()) <= Globals.attackComp.get(entity).range && target.getPos() != null) {
                state.action = Action.BOWDRAW;
            } else {
                state.action = Action.WALK;
            }
            boolean finished = ani.isAnimationFinished(state.action, state.direction);
            if (finished && state.action == Action.BOWDRAW) {
                state.action = Action.BOWRELEASE;
                EntityFactory.createShot(attackComp.get(entity).weapon.make().add(Conquest.engine.createComponent(AllianceComponent.class).create(allianceComp.get(entity).side)), transform, target.getPos());
                ani.aniTime = 0;
            }
            if (finished && state.action == Action.BOWRELEASE) {
                state.action = Action.BOWDRAW;
                ani.aniTime = 0;
            }


        }
        for (Entity entity : towers) {
            TargetComponent target = targetComp.get(entity);
            TransformComponent transform = transformComp.get(entity);
            AIComponent ai = aiComp.get(entity);
            StateComponent state = stateComp.get(entity);
            AnimationComponent ani = animationComp.get(entity);
            FollowComponent follow = followComp.get(entity);
            if (target == null) {

                findTarget(ai, transform, follow, entity);


            }
            if (target != null) {

                EntityFactory.createShot(attackComp.get(entity).weapon.make().add(Conquest.engine.createComponent(AllianceComponent.class).create(allianceComp.get(entity).side)), transform, target.getPos());
            }

        }
        for (Entity entity : melee) {
            TransformComponent transform = transformComp.get(entity);
            AIComponent ai = aiComp.get(entity);
            StateComponent state = stateComp.get(entity);
            AnimationComponent ani = animationComp.get(entity);
            TargetComponent target = targetComp.get(entity);
            FollowComponent follow = followComp.get(entity);

            if (target == null) {
                findTarget(ai, transform, follow, entity);
                state.action = Action.IDLE;
                continue;
            }
            if (transform.dst(target.getPos()) <= Globals.attackComp.get(entity).range && target.getPos() != null) {
                state.action = Action.ATTACK;

            } else {
                state.action = Action.WALK;
            }
            boolean finished = ani.isAnimationFinished(state.action, state.direction);
            if (finished && state.action == Action.ATTACK) {
                EntityFactory.createMelee(entity, attackComp.get(entity).weapon.make().add(Conquest.engine.createComponent(AllianceComponent.class).create(allianceComp.get(entity).side)));
                ani.aniTime = 0;

            }




        }
        for (Entity entity : spawners) {
            SpawnerComponent spawner = spawnerComp.get(entity);
            spawner.inLine.addLast(new SpawnNode(spawner.spawnable.get(spawner.spawnableKeys[MathUtils.random(spawner.spawnableKeys.length - 1)]), 3));

        }
        super.update(deltaTime);
    }

    public void findTarget(AIComponent ai, TransformComponent transform, FollowComponent follow, Entity entity) {


        for (Family f : ai.targetOrder) {
            Entity minTarget;
            Entity[] targets = Conquest.engine.getEntitiesFor(f).toArray(Entity.class);


            minTarget = Arrays.stream(targets)
                    .filter(e -> allianceComp.get(entity).side != allianceComp.get(e).side && entity != e)
                    .min(new Utils.DistanceComparator(transform)).orElse(null);
            if (minTarget == null) {
                return;
            }


            if (transform.dst(transformComp.get(minTarget)) < ai.sightDistance) {
                ai.currentTarget = f;
                follow.target = minTarget;



            }


        }
    }

}
