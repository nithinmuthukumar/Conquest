package com.nithinmuthukumar.conquest.Systems.GameModes;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.nithinmuthukumar.conquest.Components.PlayerComponent;
import com.nithinmuthukumar.conquest.Globals;
import com.nithinmuthukumar.conquest.Screens.GameOverScreen;

import static com.nithinmuthukumar.conquest.Globals.game;
import static com.nithinmuthukumar.conquest.Globals.removalComp;

//system that keeps track of enemy spawners and creates them
public class SandBoxSystem extends EntitySystem {
    ImmutableArray<Entity> players;

    public SandBoxSystem() {
        super(14);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        players = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        if (removalComp.has(Globals.player.getEntity())) {
            game.setScreen(new GameOverScreen());

        }
        super.update(deltaTime);
    }
}