package com.nithinmuthukumar.conquest.Helpers;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class CClickListener<T> extends ClickListener {
    protected T object;

    public CClickListener(T t) {
        this.object = t;
    }

}
