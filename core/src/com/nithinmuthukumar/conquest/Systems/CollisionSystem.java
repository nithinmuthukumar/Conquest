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
import com.nithinmuthukumar.conquest.Helpers.Utils;
import com.nithinmuthukumar.conquest.UIDatas.ItemData;

import java.util.Iterator;

import static com.nithinmuthukumar.conquest.Globals.*;

//Takes all entities that collided with the current entity processes them
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


            //gets the entity that has been collided with
            collidedEntity = i.next();

//            if(collidedEntity.getComponents().size()==0){
//                body.collidedEntities.removeValue(collidedEntity,true);
//                continue;
//            }
            //if the collided entity isn't being removed
            if (!removalComp.has(collidedEntity)) {
                //if the collided is a shield and the entity is a weapon the weapon is removed, otherwise we knock it back

                if (shieldComp.has(collidedEntity)) {
                    if (weaponComp.has(entity)) {

                        entity.add(engine.createComponent(RemovalComponent.class).create(0));
                        body.collidedEntities.removeValue(entity, true);
                    } else {
                        setKnockback(entity, body, collidedEntity);
                    }

                    continue;


                }


                //if the collided is a weapon and the entity has health we remove from the health
                if (weaponComp.has(collidedEntity) && healthComp.has(entity)) {

                    WeaponComponent weapon = weaponComp.get(collidedEntity);
                    HealthComponent health = healthComp.get(entity);
                    //if its poisonous we don't remove the weapon from the colliding entities because it does damage over time
                    if (!poisonComp.has(collidedEntity)) {
                        setKnockback(entity, body, collidedEntity);
                        body.collidedEntities.removeValue(collidedEntity, true);
                    }
                    health.damage(weapon.damage);

                    //draws the hurt effect
                    ParticleEffectPool.PooledEffect effect = Assets.effectPools.get("hurtEffect").obtain();
                    effect.start();
                    effect.setPosition(transformComp.get(entity).pos.x, transformComp.get(entity).pos.y);
                    renderSystem.addParticleRequest(effect);

                }


                if (equipComp.has(entity) && equippableComp.has(collidedEntity) && equipComp.get(entity).equipping) {
                    ItemData data = equippableComp.get(collidedEntity).data;
                    if (data.getType().equals("money")) {
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
        body.knockBack = new Vector2(5000, 5000).setAngle(Utils.getTargetAngle(transformComp.get(collidedEntity).pos, transformComp.get(entity).pos));

    }

}
