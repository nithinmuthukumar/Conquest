package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.Identifiers.AllyComponent;
import com.nithinmuthukumar.conquest.Components.SpawnerComponent;
import com.nithinmuthukumar.conquest.Helpers.SpawnNode;
import com.nithinmuthukumar.conquest.UIDatas.DataButton;
import com.nithinmuthukumar.conquest.UIDatas.SpawnData;

import static com.nithinmuthukumar.conquest.Globals.engine;
import static com.nithinmuthukumar.conquest.Globals.spawnerComp;

public class SpawnTable extends Table {
    private HorizontalGroup inLine;
    private HorizontalGroup troops;
    private ImmutableArray<Entity> entities;
    private int index = 0;

    public SpawnTable() {
        inLine = new HorizontalGroup();
        troops = new HorizontalGroup();
        setDebug(true);
        add(inLine);
        add(troops);
        setPosition(500, 200);


    }

    @Override
    protected void setParent(Group parent) {
        super.setParent(parent);
        entities = engine.getEntitiesFor(Family.all(SpawnerComponent.class, AllyComponent.class).get());
        if (entities.size() != 0)
            addTroops();

    }

    public void addTroops() {
        for (SpawnData spawn : Assets.spawnDatas) {
            SpawnerComponent spawner = spawnerComp.get(entities.get(index));
            if (spawnerComp.get(entities.get(index)).spawnable.contains(spawn.name)) {
                DataButton btn = new DataButton(spawn);
                btn.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        spawner.inLine.addLast(new SpawnNode(Assets.recipes.get(spawn.name), 2));
                    }
                });
                troops.addActor(btn);
            }
        }
    }


    @Override
    public void act(float delta) {


        super.act(delta);
    }
}
