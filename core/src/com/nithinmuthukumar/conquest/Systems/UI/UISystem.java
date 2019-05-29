package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.GameMap;

import static com.badlogic.gdx.Input.Keys.*;


public class UISystem extends EntitySystem {
    private Stage stage;
    private GameMap gameMap;
    private BuildTable buildTable;
    private MapTable mapTable;
    private InventoryTable inventoryTable;
    private int toggled;




    private SpawnTable spawnTable;

    public UISystem(GameMap gameMap) {
        super(6);
        stage=new Stage();
        this.gameMap=gameMap;
        this.mapTable = new MapTable();
        this.buildTable = new BuildTable(gameMap);
        this.spawnTable = new SpawnTable();
        inventoryTable = new InventoryTable();
        toggled = -1;


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
                    if (spawnTable.getStage() == null) {
                        toggled = S;

                        stage.addActor(spawnTable);
                    } else {
                        toggled = -1;

                        spawnTable.remove();
                    }

                }
                if (keycode == M) {
                    Conquest.client.getInputHandler().flip();
                    if (mapTable.getStage() == null) {
                        stage.addActor(mapTable);
                    } else {
                        mapTable.remove();
                    }
                }
                if (keycode == I) {
                    Conquest.client.getInputHandler().flip();

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
