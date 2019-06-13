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


        RemovalComponent removal = removalComp.get(entity);
        if (removal.countDown <= 0) {

            if (dropComp.has(entity)) {
                Vector2 pos = transformComp.get(entity).pos;
                DropComponent drops = dropComp.get(entity);
                for (int i = 0; i < drops.nums; i++) {
                    Entity e;
                    if (!bombComp.has(entity)) {
                        e = EntityFactory.createItem(Assets.itemDatas.get(drops.drops[MathUtils.random(drops.drops.length) - 1]), pos.x, pos.y);
                        e.add(engine.createComponent(VelocityComponent.class).create(1f))
                                .add(engine.createComponent(TargetComponent.class)
                                        .create(new Vector2(pos.x + MathUtils.random(-drops.range, drops.range), pos.y + MathUtils.random(-drops.range, drops.range))));
                    } else {
                        e = Assets.recipes.get(drops.drops[0]).make();
                        bodyComp.get(e).body.setTransform(pos.x, pos.y, 0);
                        transformComp.get(e).pos = pos.cpy();
                    }


                    engine.addEntity(e);
                }

            }


            getEngine().removeEntity(entity);

        }
        removal.countDown -= deltaTime;



    }
}
