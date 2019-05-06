package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.AttackComponent;
import com.nithinmuthukumar.conquest.Components.VelocityComponent;
import com.nithinmuthukumar.conquest.Globals;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import static com.nithinmuthukumar.conquest.Globals.attackComp;
import static com.nithinmuthukumar.conquest.Globals.transformComp;

public class TowerSystem extends IteratingSystem {
    public TowerSystem() {
        super(Family.all(AttackComponent.class).exclude(VelocityComponent.class).get(),5);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        AttackComponent attack=attackComp.get(entity);
        attack.timer-=deltaTime;
        if(attack.timer<=0) {

            for (Entity enemy : Globals.engine.getEntitiesFor(Family.exclude(Utils.getAlliance(entity).getClass()).get())) {

                if (transformComp.get(enemy).dst(transformComp.get(entity)) <= attack.range) {
                    /*Entity shot = EntityFactory.createShot(attack.weapon.make());
                    if (allyComp.has(entity)) {
                        shot.add(engine.createComponent(AllyComponent.class));
                    } else {
                        shot.add(engine.createComponent(EnemyComponent.class));
                    }

                     */

                }
            }
            attack.timer=attack.coolDown;
        }







    }
}
