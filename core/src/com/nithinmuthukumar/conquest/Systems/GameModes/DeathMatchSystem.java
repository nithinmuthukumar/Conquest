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

//system that checks if the entity is dead and when it dies removes it from the inputhandlers
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

        //if the entity is being removed
        if (removalComp.has(Globals.player.getEntity())) {
            Globals.conquestClient.getInputHandler().disable();
            Globals.conquestClient.getClient().sendTCP(new PlayerDeathMessage());
            Globals.game.setScreen(new GameOverScreen());

        }
        super.update(deltaTime);
    }
}
