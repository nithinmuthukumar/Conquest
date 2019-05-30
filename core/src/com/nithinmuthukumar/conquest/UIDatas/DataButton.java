package com.nithinmuthukumar.conquest.UIDatas;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nithinmuthukumar.conquest.Assets;

public class DataButton extends ImageButton {
    private Data data;

    public DataButton(Data data) {
        //super(Assets.style,"inventoryButton");
        super(Assets.style);
        setData(data);


        //add(data.name);
        //add(String.format("Cost: %d",data.cost));
    }

    public DataButton() {
        super(Assets.style);

    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
        if (data == null) {
            return;
        }
        setSize(data.icon.getRegionWidth(), data.icon.getRegionHeight());


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
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);

        if (data != null) {
            if (data.icon.getRegionWidth() > getWidth() || data.icon.getRegionHeight() > getHeight()) {
                batch.draw(data.icon, getX(), getY(), getWidth(), getHeight());
            } else {
                batch.draw(data.icon, getX(), getY());
            }
        }
    }
}
