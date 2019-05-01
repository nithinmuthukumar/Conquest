package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.nithinmuthukumar.conquest.Components.AttackComponent;
import com.nithinmuthukumar.conquest.Components.Identifiers.EnemyComponent;
import com.nithinmuthukumar.conquest.Components.Identifiers.PlayerComponent;
import com.nithinmuthukumar.conquest.Components.TargetComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.Components.VelocityComponent;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;

import static com.nithinmuthukumar.conquest.Globals.attackComp;
import static com.nithinmuthukumar.conquest.Globals.transformComp;

public class TowerSystem extends IteratingSystem {
    ImmutableArray<Entity> enemies;
    public TowerSystem() {
        super(Family.all(AttackComponent.class).exclude(VelocityComponent.class).get(),5);
    }

    @Override
    public void addedToEngine(Engine engine) {
        enemies=engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
        super.addedToEngine(engine);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        AttackComponent attack=attackComp.get(entity);
        attack.timer-=deltaTime;
        if(attack.timer<=0) {

            for (Entity enemy : enemies) {

                if (transformComp.get(enemy).dst(transformComp.get(entity)) <= attack.range) {
                    EntityFactory.createShot(attack.weapon.make(), transformComp.get(entity), transformComp.get(enemy));

                }
            }
            attack.timer=attack.coolDown;
        }







    }
}
