package com.nithinmuthukumar.conquest.Systems.GameModes;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.AIComponent;
import com.nithinmuthukumar.conquest.Components.AllianceComponent;
import com.nithinmuthukumar.conquest.Components.SpawnerComponent;
import com.nithinmuthukumar.conquest.Components.VelocityComponent;
import com.nithinmuthukumar.conquest.Globals;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;
import com.nithinmuthukumar.conquest.Screens.GameOverScreen;

import static com.nithinmuthukumar.conquest.Globals.*;

//system that keeps track of enemy spawners and creates them
public class SandBoxSystem extends EntitySystem {
    ImmutableArray<Entity> spawners;



    public SandBoxSystem() {
        super(14);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        spawners = engine.getEntitiesFor(Family.all(AIComponent.class, SpawnerComponent.class).exclude(VelocityComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        if (removalComp.has(Globals.player.getEntity())) {
            game.setScreen(new GameOverScreen());

        }
        for (int i = spawners.size(); i < 30; i++) {
            EntityFactory.createBuilding(MathUtils.random(100, 3100), MathUtils.random(100, 3100), Assets.buildingDatas.get(new String[]{"barracks", "dark barracks"}[MathUtils.random(0, 1)])).add(Globals.engine.createComponent(AllianceComponent.class).create(0)).add(Globals.engine.createComponent(AIComponent.class));

        }
        //the score in this mode is based on how long you survive
        player.setScore(deltaTime + player.getScore());
        super.update(deltaTime);
    }
}