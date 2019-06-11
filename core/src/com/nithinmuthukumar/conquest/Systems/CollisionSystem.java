package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Components.HealthComponent;
import com.nithinmuthukumar.conquest.Components.RemovalComponent;
import com.nithinmuthukumar.conquest.Components.WeaponComponent;

import java.util.Iterator;

import static com.nithinmuthukumar.conquest.Conquest.engine;
import static com.nithinmuthukumar.conquest.Globals.*;

//collisionSystem needs to follow the philosophy of only touch yourself
public class CollisionSystem extends IteratingSystem {
    public CollisionSystem() {
        super(Family.all(BodyComponent.class).exclude(RemovalComponent.class).get(), 7);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        BodyComponent body=bodyComp.get(entity);

        Iterator<Entity> i = body.collidedEntities.iterator();
        Entity collidedEntity;
        while (i.hasNext()) {
            if (builtComp.has(entity)) {
                System.out.println("damage");
            }


            collidedEntity = i.next();

//            if(collidedEntity.getComponents().size()==0){
//                body.collidedEntities.removeValue(collidedEntity,true);
//                continue;
//            }

            if (!removalComp.has(collidedEntity)) {


                if (shieldComp.has(collidedEntity) && weaponComp.has(entity)) {

                    entity.add(engine.createComponent(RemovalComponent.class).create(0));
                    body.collidedEntities.removeValue(entity, true);

                    continue;


                }


                if (weaponComp.has(collidedEntity) && healthComp.has(entity)) {

                    WeaponComponent weapon = weaponComp.get(collidedEntity);
                    HealthComponent health = healthComp.get(entity);
                    health.damage(weapon.damage);


                }


                if (equipComp.has(entity) && equippableComp.has(collidedEntity) && equipComp.get(entity).equipping) {


                    equipComp.get(entity).addToInventory(equippableComp.get(collidedEntity).data);
                    collidedEntity.add(engine.createComponent(RemovalComponent.class).create(0));
                    body.collidedEntities.removeValue(collidedEntity, true);
                }

                if (collisionRemoveComp.has(entity)) {
                    entity.add(engine.createComponent(RemovalComponent.class).create(0));
                    body.collidedEntities.removeValue(collidedEntity, true);


                }


            }
        }

    }

}
