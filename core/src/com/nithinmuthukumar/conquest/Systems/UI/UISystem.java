package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.nithinmuthukumar.conquest.GameMap;
import com.nithinmuthukumar.conquest.Systems.PlayerController;

import static com.nithinmuthukumar.conquest.Globals.inputHandler;

public class UISystem extends EntitySystem {
    private Stage stage;
    private GameMap gameMap;
    private BuildSystem buildingUISystem;
    private MapSystem mapUISystem;
    private SpawnSystem spawnSystem;
    private PlayerController controller;
    //creates the building ui which allows player to pla
    Listener<Integer> buildingUIToggle = (signal, keycode) -> {
        if (keycode == Input.Keys.B) {

            controller.flip();
            buildingUISystem.getUI().setVisible(!buildingUISystem.getUI().isVisible());
        }
    };
    public UISystem(GameMap gameMap, PlayerController controller){
        super(6);
        stage=new Stage();
        this.gameMap=gameMap;
        this.controller=controller;
        inputHandler.addListener("keyUp", buildingUIToggle);
        spawnSystem =new SpawnSystem();
        mapUISystem=new MapSystem();
        buildingUISystem = new BuildSystem(gameMap);


        stage.addActor(mapUISystem.getMap());
        stage.addActor(buildingUISystem.getUI());
        //stage.addActor(spawnSystem.getTable());





    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        engine.addSystem(mapUISystem);
        engine.addSystem(buildingUISystem);
        //engine.addSystem(spawnSystem);
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
