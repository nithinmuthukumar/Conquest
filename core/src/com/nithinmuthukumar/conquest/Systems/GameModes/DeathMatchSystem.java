package com.nithinmuthukumar.conquest.Systems.GameModes;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.nithinmuthukumar.conquest.Components.PlayerComponent;
import com.nithinmuthukumar.conquest.Globals;
import com.nithinmuthukumar.conquest.Screens.GameOverScreen;
import com.nithinmuthukumar.conquest.Server.PlayerDeathMessage;

import static com.nithinmuthukumar.conquest.Globals.removalComp;

public class DeathMatchSystem extends EntitySystem {
    ImmutableArray<Entity> players;

    public DeathMatchSystem() {
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
            Globals.client.getClient().sendTCP(new PlayerDeathMessage());

        }
        if (players.size() == 1) {
            Globals.game.setScreen(new GameOverScreen());

        }
        super.update(deltaTime);
    }
}
