package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Vector2;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Components.HealthComponent;
import com.nithinmuthukumar.conquest.Components.RemovalComponent;
import com.nithinmuthukumar.conquest.Components.WeaponComponent;
import com.nithinmuthukumar.conquest.UIDatas.ItemData;

import java.util.Iterator;

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



            collidedEntity = i.next();

//            if(collidedEntity.getComponents().size()==0){
//                body.collidedEntities.removeValue(collidedEntity,true);
//                continue;
//            }

            if (!removalComp.has(collidedEntity)) {


                if (shieldComp.has(collidedEntity)) {
                    if (weaponComp.has(entity)) {

                        entity.add(engine.createComponent(RemovalComponent.class).create(0));
                        body.collidedEntities.removeValue(entity, true);
                    } else {
                        setKnockback(entity, body, collidedEntity);
                    }

                    continue;


                }


                if (weaponComp.has(collidedEntity) && healthComp.has(entity)) {

                    WeaponComponent weapon = weaponComp.get(collidedEntity);
                    HealthComponent health = healthComp.get(entity);
                    body.collidedEntities.removeValue(entity, true);
                    health.damage(weapon.damage);
                    body.collidedEntities.removeValue(collidedEntity, true);
                    setKnockback(entity, body, collidedEntity);
                    ParticleEffectPool.PooledEffect effect = Assets.effectPools.get("hurtEffect").obtain();
                    effect.setPosition(transformComp.get(entity).pos.x, transformComp.get(entity).pos.y);
                    renderSystem.addParticleRequest(effect);




                }


                if (equipComp.has(entity) && equippableComp.has(collidedEntity) && equipComp.get(entity).equipping) {
                    ItemData data = equippableComp.get(collidedEntity).data;
                    if (data.getType().equals("currency")) {
                        player.take(data);

                    } else {


                        equipComp.get(entity).addToInventory(equippableComp.get(collidedEntity).data);
                    }
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

    public void setKnockback(Entity entity, BodyComponent body, Entity collidedEntity) {
        if (body.knockBack != null) {
            return;
        }
        body.knockBack = new Vector2(5000, 5000).setAngleRad(transformComp.get(collidedEntity).pos.angle(transformComp.get(entity).pos));

    }

}
