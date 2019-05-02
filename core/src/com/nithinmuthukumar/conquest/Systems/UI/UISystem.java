package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.GameMap;
import com.nithinmuthukumar.conquest.Systems.PlayerController;

import static com.nithinmuthukumar.conquest.Globals.inputHandler;

public class UISystem extends EntitySystem {
    private Stage stage;
    private GameMap gameMap;
    private BuildTable buildTable;
    private MapTable mapTable;
    private PlayerController controller;
    //creates the building ui which allows player to pla
    Listener<Integer> buildingUIToggle = (signal, keycode) -> {
        if (keycode == Input.Keys.B) {

            controller.flip();
            if (buildTable.getStage() == null) {
                stage.addActor(buildTable);
            } else {
                buildTable.remove();
            }

        }
    };
    private SpawnTable spawnTable;
    public UISystem(GameMap gameMap, PlayerController controller){
        super(6);
        stage=new Stage();
        this.gameMap=gameMap;
        this.controller=controller;
        this.mapTable = new MapTable();
        this.buildTable = new BuildTable(gameMap);
        this.spawnTable = new SpawnTable();
        inputHandler.addListener("keyUp", buildingUIToggle);
        TextButton spawnerButton=new TextButton("barracks",Assets.style);
        spawnerButton.setPosition(400,400);
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
