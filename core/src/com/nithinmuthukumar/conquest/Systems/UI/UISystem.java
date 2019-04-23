package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.nithinmuthukumar.conquest.GameMap;
import com.nithinmuthukumar.conquest.Globals;
import com.nithinmuthukumar.conquest.Systems.PlayerController;
import com.nithinmuthukumar.conquest.Systems.UI.BuildingUISystem;

import static com.nithinmuthukumar.conquest.Globals.inputHandler;

public class UISystem extends EntitySystem {
    private Stage stage;
    private GameMap gameMap;
    //private BuildingUISystem buildingUISystem;
    private MapUISystem mapUISystem;
    private PlayerController controller;
    //creates the building ui which allows player to pla
    Listener<Integer> buildingUIToggle = (signal, keycode) -> {
        if (keycode == Input.Keys.B) {
            controller.flip();
            //buildingUISystem.getUI().setVisible(!buildingUISystem.getUI().isVisible());
        }
    };
    public UISystem(GameMap gameMap, PlayerController controller){
        stage=new Stage();
        this.gameMap=gameMap;
        inputHandler.addListener("keyUp", buildingUIToggle);
        mapUISystem=new MapUISystem();
        Globals.engine.addSystem(mapUISystem);
        //buildingUISystem = new BuildingUISystem(gameMap);
        stage.addActor(mapUISystem.getMap());
        //stage.addActor(buildingUISystem.getUI());



    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        //engine.addSystem(buildingUISystem);
    }

    @Override
    public void update(float deltaTime) {
        stage.draw();
        super.update(deltaTime);


    }

    public Stage getStage() {
        return stage;
    }
}
