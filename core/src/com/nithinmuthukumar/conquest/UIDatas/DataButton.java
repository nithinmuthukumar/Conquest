package com.nithinmuthukumar.conquest.UIDatas;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nithinmuthukumar.conquest.Assets;

public class DataButton extends ImageButton {
    private Data data;
    private Label info;

    public DataButton(Data data, String styleName) {
        //super(Assets.style,"inventoryButton");
        super(Assets.style, styleName);
        setData(data);



        //add(data.name);
        //add(String.format("Cost: %d",data.cost));
    }

    public DataButton(String styleName) {
        super(Assets.style, styleName);

    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
        if (data == null) {
            return;
        }
        addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                info = new Label("$" + data.cost, getSkin());
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                info = null;
            }
        });


        setSize(data.icon.getRegionWidth(), data.icon.getRegionHeight());

    }

    @Override
    public boolean addListener(EventListener listener) {
        return super.addListener(listener);
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
            if (info != null) {
                info.setPosition(getX(), getY() - 25);
                info.draw(batch, 1);
            }
        }
    }
}
