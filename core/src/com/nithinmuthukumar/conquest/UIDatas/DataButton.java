package com.nithinmuthukumar.conquest.UIDatas;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class DataButton extends ImageButton {
    public final Data data;

    public DataButton(Data data) {
        super(new TextureRegionDrawable(data.icon));
        setSize(data.icon.getRegionWidth(), data.icon.getRegionHeight());
        this.data = data;


        addListener(new ClickListener(){
            @Override
            public boolean isOver(Actor actor, float x, float y) {

                return super.isOver(actor, x, y);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {

                super.clicked(event, x, y);
            }
        });

        //add(data.name);
        //add(String.format("Cost: %d",data.cost));
    }
}
