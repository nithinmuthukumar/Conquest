package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.nithinmuthukumar.conquest.Components.*;

public class EquipSystem extends EntitySystem {
    private ImmutableArray<Entity> items;
    private ImmutableArray<Entity> players;

    @Override
    public void addedToEngine(Engine engine) {
        items = engine.getEntitiesFor(Family.all(
                EquippableComponent.class).get());
        players = engine.getEntitiesFor(Family.all(PlayerComponent.class,EquipComponent.class).get());
    }
}
