package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Components.Identifiers.MeleeComponent;
import com.nithinmuthukumar.conquest.Components.Identifiers.ShooterComponent;
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

    public AISystem() {
        super(6);

    }

    @Override
    public void addedToEngine(Engine engine) {
        //the different groups which all have different ai logic
        shooters = engine.getEntitiesFor(Family.all(ShooterComponent.class, AIComponent.class).exclude(RemovalComponent.class).get());
        melee = engine.getEntitiesFor(Family.all(MeleeComponent.class, AIComponent.class).exclude(RemovalComponent.class).get());
        spawners = engine.getEntitiesFor(Family.all(SpawnerComponent.class, AIComponent.class).exclude(RemovalComponent.class).get());
        super.addedToEngine(engine);
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : shooters) {
            TransformComponent transform = transformComp.get(entity);
            AIComponent ai = aiComp.get(entity);
            StateComponent state = stateComp.get(entity);
            AnimationComponent ani = animationComp.get(entity);
            TargetComponent target = targetComp.get(entity);

            if (target == null || !findTarget(ai, transform, target, entity)) {
                state.action = Action.IDLE;

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
                EntityFactory.createShot(attackComp.get(entity).weapon.make().add(Utils.getAlliance(entity)), transform, target.getPos());
                ani.aniTime = 0;
            }
            if (finished && state.action == Action.BOWRELEASE) {
                state.action = Action.BOWDRAW;
                ani.aniTime = 0;
            }


        }
        for (Entity entity : melee) {
            TransformComponent transform = transformComp.get(entity);
            AIComponent ai = aiComp.get(entity);
            StateComponent state = stateComp.get(entity);
            AnimationComponent ani = animationComp.get(entity);
            TargetComponent target = targetComp.get(entity);

            if (target == null || !findTarget(ai, transform, target, entity)) {
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
                EntityFactory.createMelee(entity, attackComp.get(entity).weapon.make().add(Utils.getAlliance(entity)));
                ani.aniTime = 0;

            }




        }
        for (Entity entity : spawners) {
            SpawnerComponent spawner = spawnerComp.get(entity);
            spawner.inLine.addLast(new SpawnNode(Assets.recipes.get(spawner.spawnable.get(spawner.spawnableKeys[MathUtils.random(spawner.spawnableKeys.length - 1)])), 3));

        }
        super.update(deltaTime);
    }

    public boolean findTarget(AIComponent ai, TransformComponent transform, TargetComponent target, Entity entity) {

        for (Family f : ai.targetOrder) {
            Entity minTarget;
            Entity[] targets = engine.getEntitiesFor(f).toArray(Entity.class);


            if (enemyComp.has(entity)) {
                minTarget = Arrays.stream(targets)
                        .filter(e -> !enemyComp.has(e) && entity != e)
                        .min(new Utils.DistanceComparator(transform)).orElse(null);
            } else {
                minTarget = Arrays.stream(targets)
                        .filter(e -> !(allyComp.has(e) || playerComp.has(e)) && entity != e)
                        .min(new Utils.DistanceComparator(transform)).orElse(null);
            }


            if (minTarget == null) {
                return false;
            }

            if (transform.dst(transformComp.get(minTarget)) < ai.sightDistance) {
                ai.currentTarget = f;
                target.create(minTarget);

                return true;


            }
        }
        return false;
    }

}
