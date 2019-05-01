package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.SpawnerComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.Helpers.Utils;
import com.nithinmuthukumar.conquest.Recipe;
import com.nithinmuthukumar.conquest.UIDatas.DataButton;
import com.nithinmuthukumar.conquest.UIDatas.SpawnData;

import static com.nithinmuthukumar.conquest.Globals.*;

public class SpawnSystem extends EntitySystem {

    private Table table;
    private HorizontalGroup inLine;
    private HorizontalGroup troops;
    private ImmutableArray<Entity> entities;
    private int index=0;

    public SpawnSystem() {
        table=new Table();
        inLine=new HorizontalGroup();
        troops=new HorizontalGroup();
        table.setVisible(false);
        table.setDebug(true);
        table.setPosition(100,500);
        table.add(inLine);
        table.add(troops);



    }

    @Override
    public void addedToEngine(Engine engine) {
        entities=engine.getEntitiesFor(Family.all(SpawnerComponent.class).get());
        super.addedToEngine(engine);
    }


    @Override
    public void update(float deltaTime) {



        super.update(deltaTime);

    }

    @Override
    public void setProcessing(boolean processing) {
        if(processing){
            addTroops();
        }
        super.setProcessing(processing);
    }

    public void addTroops(){
        for(SpawnData spawn:Assets.spawnDatas) {
            SpawnerComponent spawner=spawnerComp.get(entities.get(index));
            if(spawnerComp.get(entities.get(index)).spawnable.contains(spawn.name)) {
                troops.addActor(new DataButton(spawner,spawn));
            }
        }
    }


    public Table getTable() {
        return table;
    }


}
