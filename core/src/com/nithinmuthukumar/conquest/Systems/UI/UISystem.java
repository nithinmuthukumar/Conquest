package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.nithinmuthukumar.conquest.Conquest;

import static com.badlogic.gdx.Input.Keys.*;


public class UISystem extends EntitySystem {
    private Stage stage;
    private BuildTable buildTable;
    private MapUI mapUI;
    private InventoryTable inventoryTable;
    private int toggled;




    private SpawnTable spawnTable;

    public UISystem() {
        super(6);
        stage=new Stage();
        this.mapUI = new MapUI();
        this.buildTable = new BuildTable();
        this.spawnTable = new SpawnTable();
        inventoryTable = new InventoryTable();
        toggled = -1;
        stage.addActor(new StatsTable());


        stage.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (toggled != keycode && toggled != -1) {
                    return super.keyDown(event, keycode);
                }

                if (keycode == B) {


                    Conquest.client.getInputHandler().flip();
                    if (buildTable.getStage() == null) {
                        toggled = B;
                        stage.addActor(buildTable);
                        stage.addListener(buildTable.getTouchUpListener());
                    } else {
                        toggled = -1;
                        stage.removeListener(buildTable.getTouchUpListener());
                        buildTable.remove();
                    }
                }

                if (keycode == S) {
                    Conquest.client.getInputHandler().flip();
                    if (spawnTable.getStage() == null && spawnTable.hasSpawners()) {
                        toggled = S;

                        stage.addActor(spawnTable);
                    } else {
                        toggled = -1;

                        spawnTable.remove();
                    }

                }
                if (keycode == M) {
                    if (mapUI.getStage() == null) {
                        stage.addActor(mapUI);
                    } else {
                        mapUI.remove();
                    }
                }
                if (keycode == I) {
                    Conquest.client.getInputHandler().flip();
                    if (inventoryTable.getStage() == null) {
                        toggled = I;
                        stage.addActor(inventoryTable);


                    } else {
                        toggled = -1;
                        inventoryTable.remove();
                    }

                }

                return super.keyDown(event, keycode);
            }

        });








    }


    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

    }

    @Override
    public void update(float deltaTime) {
        stage.act();
        stage.draw();
        super.update(deltaTime);


    }

    public Stage getStage() {
        return stage;
    }
}
