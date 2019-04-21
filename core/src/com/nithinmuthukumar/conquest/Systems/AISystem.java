package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.nithinmuthukumar.conquest.Components.EnemyComponent;
import com.nithinmuthukumar.conquest.Components.PlayerComponent;
import com.nithinmuthukumar.conquest.Components.RemovalComponent;
import com.nithinmuthukumar.conquest.Components.StateComponent;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Globals;

public class AISystem extends IteratingSystem {
    public AISystem(){
        super(Family.all(EnemyComponent.class).exclude(PlayerComponent.class, RemovalComponent.class).get());
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        StateComponent state = Globals.stateComp.get(entity);

        Vector2 start = new Vector2(Globals.transformComp.get(entity).x, Globals.transformComp.get(entity).y);
        Vector2 target = Globals.targetComp.get(entity).target;
        if (start.dst(target) <= Globals.fighterComp.get(entity).range) {
            state.action = Action.ATTACK;
        } else {
            state.action = Action.WALK;
        }
    }
}
