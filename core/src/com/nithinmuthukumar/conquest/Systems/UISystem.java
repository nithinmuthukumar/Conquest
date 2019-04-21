package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class UISystem extends EntitySystem {
    private Stage stage;
    public UISystem(){
        stage=new Stage();


    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
}
