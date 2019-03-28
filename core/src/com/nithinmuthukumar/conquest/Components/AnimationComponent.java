package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nithinmuthukumar.conquest.Action;
import com.nithinmuthukumar.conquest.Direction;
import com.nithinmuthukumar.conquest.Utils;

import java.util.HashMap;

public class AnimationComponent implements Component,Cloneable {
    private HashMap<Action,HashMap<Direction,Animation<TextureRegion>>> animations;
    public AnimationComponent(String path,float speed){
        animations = new HashMap<>();
        FileHandle[] stateFiles = Utils.listFiles(new FileHandle(path));
        for (FileHandle f : stateFiles) {
            Action action = Action.valueOf(f.name());
            FileHandle[] dirFiles = Utils.listFiles(f);
            for (FileHandle d : dirFiles) {
                Direction direction = Direction.valueOf(d.nameWithoutExtension());

                TextureRegion[] frames = TextureRegion.split(new Texture(d.path()),24,30)[0];
                put(action, direction, new Animation<TextureRegion>(speed,frames));
            }
        }
    }


    public Animation<TextureRegion> get(Action action, Direction direction) {
        return animations.get(action).get(direction);
    }
    private void put(Action action, Direction direction, Animation<TextureRegion> sprites) {
        if (!animations.containsKey(action)) {
            animations.put(action, new HashMap<>());
        }
        animations.get(action).put(direction, sprites);
    }
    @Override
    protected AnimationComponent clone() throws CloneNotSupportedException{
        return (AnimationComponent)super.clone();

    }

}
