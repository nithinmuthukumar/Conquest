package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Components.HealthComponent;
import com.nithinmuthukumar.conquest.Components.RemovalComponent;
import com.nithinmuthukumar.conquest.Components.WeaponComponent;

import static com.nithinmuthukumar.conquest.Globals.*;

public class CollisionSystem extends IteratingSystem {
    public CollisionSystem() {
        super(Family.all(BodyComponent.class).exclude(RemovalComponent.class).get(),5);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BodyComponent body=bodyComp.get(entity);

        if(body.collidedEntity!=null&&!removalComp.has(body.collidedEntity)){

            if(collisionRemoveComp.get(entity)!=null){
                if (bodyComp.get(entity).collidedEntity != null) {
                    PooledEngine engine=(PooledEngine)(getEngine());
                    entity.add(engine.createComponent(RemovalComponent.class).create(4f));


                }
            }

            if(weaponComp.has(body.collidedEntity)&&healthComp.has(entity)){
                WeaponComponent weapon=weaponComp.get(body.collidedEntity);
                HealthComponent health=healthComp.get(entity);
                health.damage(weapon.damage);
                body.collidedEntity=null;
            }
            //have a clause for items and if you can equip equip it
            //else it is a wall
            else{
                //do wall stuff

            }


        }

    }
}
