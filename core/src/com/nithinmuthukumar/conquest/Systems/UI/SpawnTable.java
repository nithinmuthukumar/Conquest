package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.AllianceComponent;
import com.nithinmuthukumar.conquest.Components.CameraComponent;
import com.nithinmuthukumar.conquest.Components.SpawnerComponent;
import com.nithinmuthukumar.conquest.Helpers.SpawnNode;
import com.nithinmuthukumar.conquest.Helpers.Utils;
import com.nithinmuthukumar.conquest.UIDatas.DataButton;
import com.nithinmuthukumar.conquest.UIDatas.SpawnData;

import static com.nithinmuthukumar.conquest.Conquest.*;
import static com.nithinmuthukumar.conquest.Globals.spawnerComp;

public class SpawnTable extends Table {
    private HorizontalGroup inLine;
    private HorizontalGroup troops;
    private ImmutableArray<Entity> entities;
    private int index = 0;
    private ImageButton leftButton;
    private ImageButton rightButton;

    public SpawnTable() {
        inLine = new HorizontalGroup();
        troops = new HorizontalGroup();
        entities = engine.getEntitiesFor(Family.all(SpawnerComponent.class, AllianceComponent.class).get());

        leftButton = new ImageButton(Assets.style);
        leftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (index > 0) {
                    index -= 1;
                    changeCamera(1);

                } else {
                    index = Utils.filterAlliance(client.getClient().getID(), entities).size - 1;
                    changeCamera(-index);

                }

                troops.clear();
                addTroops();
                super.clicked(event, x, y);
            }
        });

        rightButton = new ImageButton(Assets.style);
        rightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (index < Utils.filterAlliance(client.getClient().getID(), entities).size - 1) {

                    index += 1;
                    changeCamera(-1);

                } else {
                    index = 0;
                    changeCamera(Utils.filterAlliance(client.getClient().getID(), entities).size - 1);
                }
                troops.clear();
                addTroops();
                super.clicked(event, x, y);
            }
        });


    }


    @Override
    protected void setParent(Group parent) {


        super.setParent(parent);

        Array<Entity> spawners = Utils.filterAlliance(client.getClient().getID(), entities);

        if (parent == null) {
            clearChildren();
            troops.clear();
            inLine.clear();
            spawners.get(index).remove(CameraComponent.class);


            player.getEntity().add(engine.createComponent(CameraComponent.class));


        } else {

            add(leftButton);
            add(inLine);
            add(rightButton);
            row();
            add(troops);
            setPosition(500, 200);
            addTroops();


            player.getEntity().remove(CameraComponent.class);
            spawners.get(index).add(engine.createComponent(CameraComponent.class));


        }

    }

    public boolean hasSpawners() {
        return Utils.filterAlliance(client.getClient().getID(), entities).size > 0;
    }

    public void addTroops() {
        for (SpawnData spawn : Assets.spawnDatas.values()) {
            Array<Entity> spawners = Utils.filterAlliance(client.getClient().getID(), entities);
            SpawnerComponent spawner = spawnerComp.get(spawners.get(index));
            if (spawner.spawnable.contains(spawn.name)) {

                DataButton btn = new DataButton(spawn);
                btn.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        spawner.inLine.addLast(new SpawnNode(spawn.name, 2));
                    }
                });
                troops.addActor(btn);
            }
        }
    }

    //changes which spawner holds the camera component by passing in the difference between the current index and the previous index
    //this is only done because I don't want to create a temporary variable to hold the index
    public void changeCamera(int prev) {
        Array<Entity> spawners = Utils.filterAlliance(client.getClient().getID(), entities);
        spawners.get(index + prev).remove(CameraComponent.class);
        spawners.get(index).add(engine.createComponent(CameraComponent.class));


    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

}
