package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.DropComponent;
import com.nithinmuthukumar.conquest.Components.RemovalComponent;
import com.nithinmuthukumar.conquest.Components.TargetComponent;
import com.nithinmuthukumar.conquest.Components.VelocityComponent;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;

import static com.nithinmuthukumar.conquest.Globals.*;

public class RemovalSystem extends IteratingSystem {


    public RemovalSystem() {
        super(Family.all(RemovalComponent.class).get(), 10);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //if the time left before removal is zero it can be removed
        RemovalComponent removal = removalComp.get(entity);
        if (removal.countDown <= 0) {
            //if the entity has drops when it dies the drops are spawned as items unless it is a bomb
            if (dropComp.has(entity)) {
                Vector2 pos = transformComp.get(entity).pos;
                DropComponent drops = dropComp.get(entity);
                //drops.nums determines the number of drops that will be spawned
                int portion = 360 / drops.nums;
                for (int i = 0; i < drops.nums; i++) {
                    Entity e;
                    Vector2 targetPos = new Vector2(pos.x + drops.range * MathUtils.cosDeg(portion * i), pos.y + drops.range * MathUtils.sinDeg(portion * i));
                    if (!explodeComp.has(entity)) {
                        //the item is spawned and travels to the position where it will stop which is randomly generated
                        e = EntityFactory.createItem(Assets.itemDatas.get(drops.drops[drops.nums % drops.drops.length]), pos.x, pos.y);
                        e.add(engine.createComponent(VelocityComponent.class).create(1f))
                                .add(engine.createComponent(TargetComponent.class)
                                        .create(targetPos));
                    } else {
                        //the explosion is spawned to where the position of the entity is
                        e = Assets.recipes.get(drops.drops[0]).make();
                        bodyComp.get(e).body.setTransform(targetPos.x, targetPos.y, 0);
                        transformComp.get(e).pos = pos.cpy();
                    }


                    engine.addEntity(e);
                }

            }

            //removes the entity from the engine
            getEngine().removeEntity(entity);

        }
        //counts down the delta time
        removal.countDown -= deltaTime;



    }
}
