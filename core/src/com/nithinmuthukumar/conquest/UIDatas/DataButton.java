package com.nithinmuthukumar.conquest.UIDatas;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.SpawnerComponent;
import com.nithinmuthukumar.conquest.Helpers.SpawnNode;

public class DataButton extends ImageButton {

    public DataButton(SpawnerComponent spawner, Data data) {
        super(new TextureRegionDrawable(data.icon));

        addListener(new ClickListener(){
            @Override
            public boolean isOver(Actor actor, float x, float y) {

                return super.isOver(actor, x, y);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                spawner.inLine.addLast(new SpawnNode(Assets.recipes.get(data.name),1));
                super.clicked(event, x, y);
            }
        });

        //add(data.name);
        //add(String.format("Cost: %d",data.cost));
    }
}
