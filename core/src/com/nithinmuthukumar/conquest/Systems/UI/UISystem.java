package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.nithinmuthukumar.conquest.Systems.ShapeRenderSystem;

import static com.badlogic.gdx.Input.Keys.*;
import static com.nithinmuthukumar.conquest.Globals.conquestClient;


public class UISystem extends EntitySystem {
    private Stage stage;
    private BuildTable buildTable;
    private MapUI mapUI;
    private InventoryTable inventoryTable;
    private int toggled;
    private Shop shop;




    private SpawnTable spawnTable;

    public UISystem(ShapeRenderSystem shapeRenderSystem) {
        super(12);
        stage=new Stage();
        this.mapUI = new MapUI();

        mapUI.getSignal().add(shapeRenderSystem.drawRequestListener);
        this.buildTable = new BuildTable();
        this.spawnTable = new SpawnTable();
        shop = new Shop();

        inventoryTable = new InventoryTable();
        toggled = -1;
        stage.addActor(new StatsTable());


        stage.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == ESCAPE) {
                    mapUI.makeSmall();
                }
                if (keycode == M) {
                    if (mapUI.getStage() == null) {
                        stage.addActor(mapUI);
                    } else {
                        mapUI.makeSmall();
                        mapUI.remove();
                    }
                }

                if (!mapUI.isSmall()) {
                    return false;
                }
                if (toggled != keycode && toggled != -1) {
                    return super.keyDown(event, keycode);
                }
                if (keycode == O) {
                    conquestClient.getInputHandler().flip();
                    if (shop.getStage() == null) {
                        toggled = O;
                        stage.addActor(shop);
                    } else {
                        toggled = -1;
                        shop.remove();
                    }
                }

                if (keycode == B) {


                    conquestClient.getInputHandler().flip();
                    if (buildTable.getStage() == null) {
                        toggled = B;
                        stage.addActor(buildTable);
                        stage.addListener(buildTable.getListener());
                    } else {
                        toggled = -1;
                        stage.removeListener(buildTable.getListener());
                        buildTable.remove();
                    }
                }


                if (keycode == S) {
                    conquestClient.getInputHandler().flip();
                    if (spawnTable.getStage() == null && spawnTable.hasSpawners()) {
                        toggled = S;

                        stage.addActor(spawnTable);
                    } else {
                        toggled = -1;

                        spawnTable.remove();
                    }

                }

                if (keycode == I) {
                    conquestClient.getInputHandler().flip();
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
