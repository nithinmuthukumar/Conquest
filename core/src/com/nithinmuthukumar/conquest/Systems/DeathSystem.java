package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;

import static com.nithinmuthukumar.conquest.Globals.*;

public class DeathSystem extends IteratingSystem {
    public DeathSystem() {
        super(Family.all(HealthComponent.class).exclude(RemovalComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Body body = bodyComp.get(entity).body;
        DropComponent drop = dropComp.get(entity);
        if(healthComp.get(entity).health<=0){
            if (dropComp.has(entity)) {
                for (int i = 0; i < drop.nums; i++) {
                    Entity e = EntityFactory.createItem(Assets.itemDatas.get(drop.drops[0]), body.getPosition().x + MathUtils.random(-drop.range, drop.range), body.getPosition().y + MathUtils.random(-drop.range, drop.range));
                    e.add(Conquest.engine.createComponent(VelocityComponent.class).create(100f))
                            .add(Conquest.engine.createComponent(TargetComponent.class)
                                    .create(new Vector2(body.getPosition().x + MathUtils.random(-drop.range, drop.range), body.getPosition().y + MathUtils.random(-drop.range, drop.range))));


                    Conquest.engine.addEntity(e);
                }

            }

            PooledEngine engine=(PooledEngine)(getEngine());

            entity.add(engine.createComponent(RemovalComponent.class).create(2f));
        }

    }
}
