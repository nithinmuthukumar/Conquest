package com.nithinmuthukumar.conquest.Helpers;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

//click listener which allows an object to be passed in
//necessary when listeners are created in for loop or need a reference to the button itself
public class CClickListener<T> extends ClickListener {
    protected T object;

    public CClickListener(T t) {
        this.object = t;
    }

}
