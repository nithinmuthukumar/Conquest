package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.GameMap;


public class UISystem extends EntitySystem {
    private Stage stage;
    private GameMap gameMap;
    private BuildTable buildTable;
    private MapTable mapTable;
    private InventoryTable inventoryTable;



    private SpawnTable spawnTable;

    public UISystem(GameMap gameMap) {
        super(6);
        stage=new Stage();
        this.gameMap=gameMap;
        this.mapTable = new MapTable();
        this.buildTable = new BuildTable(gameMap);
        this.spawnTable = new SpawnTable();
        inventoryTable = new InventoryTable();
        stage.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                //b toggles building
                if (keycode == Input.Keys.B) {

                    Conquest.client.flip();
                    if (buildTable.getStage() == null) {
                        stage.addActor(buildTable);
                        stage.addListener(buildTable.getTouchUpListener());
                    } else {
                        stage.removeListener(buildTable.getTouchUpListener());
                        buildTable.remove();
                    }

                }
                return super.keyDown(event, keycode);
            }
        });
        TextButton spawnerButton=new TextButton("barracks",Assets.style);
        spawnerButton.setPosition(0, 400);
        spawnerButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (spawnTable.getStage() == null) {
                    stage.addActor(spawnTable);

                } else {
                    spawnTable.remove();
                }

                super.clicked(event, x, y);
            }
        });




        stage.addActor(spawnerButton);
        stage.addActor(mapTable.getMap());
        stage.addActor(inventoryTable);






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
